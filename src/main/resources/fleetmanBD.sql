-- Créer la base de donnée si ce n'est pas déjà fait
CREATE DATABASE IF NOT EXISTS fleetmanBD;

-- Activer l'extension PostGIS si ce n'est pas déjà fait
CREATE EXTENSION IF NOT EXISTS postgis;

-- Table user
CREATE TABLE "user" (
    userId SERIAL PRIMARY KEY,
    userName VARCHAR(100) NOT NULL,
    userPassword VARCHAR(255) NOT NULL, -- Longueur suffisante pour bcrypt/argon2
    userEmail VARCHAR(150) NOT NULL UNIQUE,
    userPhoneNumber VARCHAR(20) NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table driver
CREATE TABLE driver (
    driverId SERIAL PRIMARY KEY,
    driverName VARCHAR(100) NOT NULL,
    driverEmail VARCHAR(150) NOT NULL UNIQUE,
    driverPhoneNumber VARCHAR(20) NOT NULL,
    emergencyContactName VARCHAR(100),
    emergencyContact VARCHAR(20),
    personnalInformations TEXT,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table vehicle
CREATE TABLE vehicle (
    vehicleId SERIAL PRIMARY KEY,
    vehicleMake VARCHAR(50) NOT NULL,
    vehicleName VARCHAR(100) NOT NULL,
    vehicleRegistrationNumber VARCHAR(20) NOT NULL UNIQUE,
    vehicleType VARCHAR(50) NOT NULL,
    vehicleImage TEXT,
    vehicleDocument TEXT,
    vehicleIotAddress VARCHAR(100),
    vehicleFuelLevel DECIMAL(5,2) CHECK (vehicleFuelLevel >= 0 AND vehicleFuelLevel <= 100),
    vehicleNumberPassengers INTEGER CHECK (vehicleNumberPassengers > 0),
    vehicleSpeed DECIMAL(6,2) CHECK (vehicleSpeed >= 0),
    userId INTEGER NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vehicle_user FOREIGN KEY (userId) 
        REFERENCES "user"(userId) ON DELETE CASCADE
);

-- Table de liaison driver-vehicle (Many-to-Many)
CREATE TABLE driver_vehicle (
    driverId INTEGER NOT NULL,
    vehicleId INTEGER NOT NULL,
    assignedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (driverId, vehicleId),
    CONSTRAINT fk_driver_vehicle_driver FOREIGN KEY (driverId) 
        REFERENCES driver(driverId) ON DELETE CASCADE,
    CONSTRAINT fk_driver_vehicle_vehicle FOREIGN KEY (vehicleId) 
        REFERENCES vehicle(vehicleId) ON DELETE CASCADE
);

-- Table notification
CREATE TABLE notification (
    notificationId SERIAL PRIMARY KEY,
    notificationSubject VARCHAR(200) NOT NULL,
    notificationContent TEXT NOT NULL,
    notificationDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notificationState BOOLEAN NOT NULL DEFAULT FALSE,
    userId INTEGER NOT NULL,
    CONSTRAINT fk_notification_user FOREIGN KEY (userId) 
        REFERENCES "user"(userId) ON DELETE CASCADE
);

-- Table fuelRecharge
CREATE TABLE fuelRecharge (
    rechargeId SERIAL PRIMARY KEY,
    rechargeQuantity DECIMAL(8,2) NOT NULL CHECK (rechargeQuantity > 0),
    rechargePrice DECIMAL(10,2) NOT NULL CHECK (rechargePrice >= 0),
    rechargePoint GEOMETRY(Point, 4326), -- Utilisation du type Point PostGIS
    rechargeDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicleId INTEGER NOT NULL,
    CONSTRAINT fk_fuelrecharge_vehicle FOREIGN KEY (vehicleId) 
        REFERENCES vehicle(vehicleId) ON DELETE CASCADE
);

-- Table maintenance
CREATE TABLE maintenance (
    maintenanceId SERIAL PRIMARY KEY,
    maintenanceDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    maintenancePoint GEOMETRY(Point, 4326), -- Utilisation du type Point PostGIS
    maintenanceSubject VARCHAR(200) NOT NULL,
    maintenanceCost DECIMAL(10,2) NOT NULL CHECK (maintenanceCost >= 0),
    vehicleId INTEGER NOT NULL,
    CONSTRAINT fk_maintenance_vehicle FOREIGN KEY (vehicleId) 
        REFERENCES vehicle(vehicleId) ON DELETE CASCADE
);

-- Table trip
CREATE TABLE trip (
    tripId SERIAL PRIMARY KEY,
    departurePoint GEOMETRY(Point, 4326) NOT NULL, -- Utilisation du type Point PostGIS
    arrivalPoint GEOMETRY(Point, 4326) NOT NULL, -- Utilisation du type Point PostGIS
    departureDateTime TIMESTAMP NOT NULL,
    arrivalDateTime TIMESTAMP,
    driverId INTEGER NOT NULL,
    vehicleId INTEGER NOT NULL,
    CONSTRAINT chk_valid_trip_dates CHECK (departureDateTime <= arrivalDateTime),
    CONSTRAINT fk_trip_driver FOREIGN KEY (driverId) 
        REFERENCES driver(driverId) ON DELETE CASCADE,
    CONSTRAINT fk_trip_vehicle FOREIGN KEY (vehicleId) 
        REFERENCES vehicle(vehicleId) ON DELETE CASCADE
);

-- Table position (pour les positions en temps réel)
CREATE TABLE position (
    positionId BIGSERIAL PRIMARY KEY,
    coordinate GEOMETRY(Point, 4326) NOT NULL, -- Utilisation du type Point PostGIS
    positionDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicleId INTEGER NOT NULL,
    CONSTRAINT fk_position_vehicle FOREIGN KEY (vehicleId) 
        REFERENCES vehicle(vehicleId) ON DELETE CASCADE
);

-- Table positionHistory (pour l'historique des positions) avec LineString
CREATE TABLE positionHistory (
    positionHistoryId SERIAL PRIMARY KEY,
    summaryCoordinate GEOMETRY(LineString, 4326) NOT NULL,
    positionDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicleId INTEGER NOT NULL,
    CONSTRAINT fk_positionhistory_vehicle FOREIGN KEY (vehicleId) 
        REFERENCES vehicle(vehicleId) ON DELETE CASCADE
);

-- Indexes pour améliorer les performances des requêtes spatiales
CREATE INDEX idx_vehicle_position ON vehicle USING GIST(position);
CREATE INDEX idx_fuelrecharge_point ON fuelRecharge USING GIST(rechargePoint);
CREATE INDEX idx_maintenance_point ON maintenance USING GIST(maintenancePoint);
CREATE INDEX idx_trip_departure ON trip USING GIST(departurePoint);
CREATE INDEX idx_trip_arrival ON trip USING GIST(arrivalPoint);
CREATE INDEX idx_position_coordinate ON position USING GIST(coordinate);
CREATE INDEX idx_positionhistory_linestring ON positionHistory USING GIST(summaryCoordinate);

-- Indexes pour les clés étrangères fréquemment utilisées
CREATE INDEX idx_vehicle_user ON vehicle(userId);
CREATE INDEX idx_notification_user ON notification(userId);
CREATE INDEX idx_fuelrecharge_vehicle ON fuelRecharge(vehicleId);
CREATE INDEX idx_maintenance_vehicle ON maintenance(vehicleId);
CREATE INDEX idx_trip_driver ON trip(driverId);
CREATE INDEX idx_trip_vehicle ON trip(vehicleId);
CREATE INDEX idx_drivervehicle_driver ON driver_vehicle(driverId);
CREATE INDEX idx_drivervehicle_vehicle ON driver_vehicle(vehicleId);
CREATE INDEX idx_position_vehicle ON position(vehicleId);
CREATE INDEX idx_positionhistory_vehicle ON positionHistory(vehicleId);

-- Indexes pour les timestamps (requêtes temporelles fréquentes)
CREATE INDEX idx_position_time ON position(positionTime);
CREATE INDEX idx_positionhistory_date ON positionHistory(positionDate);
CREATE INDEX idx_trip_departure_time ON trip(departureDateTime);
CREATE INDEX idx_fuelrecharge_datetime ON fuelRecharge(rechargeDateTime);
CREATE INDEX idx_maintenance_datetime ON maintenance(maintenanceDateTime);



-- les triggers sont utilisés pour automatiquement mettre a jour la date de dernière modif des tables
-- users, driver et vehicle .

-- Fonction trigger pour mettre à jour automatiquement updatedAt
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updatedAt = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers pour les tables avec updatedAt
CREATE TRIGGER update_user_updated_at BEFORE UPDATE ON "user"
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_driver_updated_at BEFORE UPDATE ON driver
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_vehicle_updated_at BEFORE UPDATE ON vehicle
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
