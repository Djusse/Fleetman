-- Activer l'extension PostGIS si ce n'est pas déjà fait
CREATE EXTENSION IF NOT EXISTS postgis;

-- Table user (avec guillemets car USER est un mot réservé)
CREATE TABLE "user" (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(150) NOT NULL UNIQUE,
    user_phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table driver
CREATE TABLE driver (
    driver_id SERIAL PRIMARY KEY,
    driver_name VARCHAR(100) NOT NULL,
    driver_email VARCHAR(150) NOT NULL UNIQUE,
    driver_phone_number VARCHAR(20) NOT NULL,
    emergency_contact_name VARCHAR(100),
    emergency_contact VARCHAR(20),
    personal_informations TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table vehicle
CREATE TABLE vehicle (
    vehicle_id SERIAL PRIMARY KEY,
    vehicle_make VARCHAR(50) NOT NULL,
    vehicle_name VARCHAR(100) NOT NULL,
    vehicle_registration_number VARCHAR(20) NOT NULL UNIQUE,
    vehicle_type VARCHAR(50) NOT NULL,
    vehicle_image TEXT,
    vehicle_document TEXT,
    vehicle_iot_address VARCHAR(100),
    vehicle_fuel_level DECIMAL(5,2) CHECK (vehicle_fuel_level >= 0 AND vehicle_fuel_level <= 100),
    vehicle_number_passengers INTEGER CHECK (vehicle_number_passengers > 0),
    vehicle_speed DECIMAL(6,2) CHECK (vehicle_speed >= 0),
    user_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vehicle_user FOREIGN KEY (user_id)
        REFERENCES "user"(user_id) ON DELETE CASCADE
);

-- Table de liaison driver-vehicle (Many-to-Many)
CREATE TABLE driver_vehicle (
    driver_id INTEGER NOT NULL,
    vehicle_id INTEGER NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (driver_id, vehicle_id),
    CONSTRAINT fk_driver_vehicle_driver FOREIGN KEY (driver_id)
        REFERENCES driver(driver_id) ON DELETE CASCADE,
    CONSTRAINT fk_driver_vehicle_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- Table notification
CREATE TABLE notification (
    notification_id SERIAL PRIMARY KEY,
    notification_subject VARCHAR(200) NOT NULL,
    notification_content TEXT NOT NULL,
    notification_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notification_state BOOLEAN NOT NULL DEFAULT FALSE,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id)
        REFERENCES "user"(user_id) ON DELETE CASCADE
);

-- Table fuel_recharge
CREATE TABLE fuel_recharge (
    recharge_id SERIAL PRIMARY KEY,
    recharge_quantity DECIMAL(8,2) NOT NULL CHECK (recharge_quantity > 0),
    recharge_price DECIMAL(10,2) NOT NULL CHECK (recharge_price >= 0),
    recharge_point GEOMETRY(Point, 4326),
    recharge_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicle_id INTEGER NOT NULL,
    CONSTRAINT fk_fuel_recharge_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- Table maintenance
CREATE TABLE maintenance (
    maintenance_id SERIAL PRIMARY KEY,
    maintenance_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    maintenance_point GEOMETRY(Point, 4326),
    maintenance_subject VARCHAR(200) NOT NULL,
    maintenance_cost DECIMAL(10,2) NOT NULL CHECK (maintenance_cost >= 0),
    vehicle_id INTEGER NOT NULL,
    CONSTRAINT fk_maintenance_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- Table trip
CREATE TABLE trip (
    trip_id SERIAL PRIMARY KEY,
    departure_point GEOMETRY(Point, 4326) NOT NULL,
    arrival_point GEOMETRY(Point, 4326) NOT NULL,
    departure_date_time TIMESTAMP NOT NULL,
    arrival_date_time TIMESTAMP,
    driver_id INTEGER NOT NULL,
    vehicle_id INTEGER NOT NULL,
    CONSTRAINT chk_valid_trip_dates CHECK (departure_date_time <= arrival_date_time),
    CONSTRAINT fk_trip_driver FOREIGN KEY (driver_id)
        REFERENCES driver(driver_id) ON DELETE CASCADE,
    CONSTRAINT fk_trip_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- Table position
CREATE TABLE position (
    position_id BIGSERIAL PRIMARY KEY,
    coordinate GEOMETRY(Point, 4326) NOT NULL,
    position_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicle_id INTEGER NOT NULL,
    CONSTRAINT fk_position_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- Table position_history
CREATE TABLE position_history (
    position_history_id SERIAL PRIMARY KEY,
    summary_coordinate GEOMETRY(LineString, 4326) NOT NULL,
    position_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vehicle_id INTEGER NOT NULL,
    CONSTRAINT fk_position_history_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(vehicle_id) ON DELETE CASCADE
);

-- Indexes pour améliorer les performances des requêtes spatiales
CREATE INDEX idx_vehicle_position ON vehicle USING GIST(position);
CREATE INDEX idx_fuel_recharge_point ON fuel_recharge USING GIST(recharge_point);
CREATE INDEX idx_maintenance_point ON maintenance USING GIST(maintenance_point);
CREATE INDEX idx_trip_departure ON trip USING GIST(departure_point);
CREATE INDEX idx_trip_arrival ON trip USING GIST(arrival_point);
CREATE INDEX idx_position_coordinate ON position USING GIST(coordinate);
CREATE INDEX idx_position_history_linestring ON position_history USING GIST(summary_coordinate);

-- Indexes pour les clés étrangères fréquemment utilisées
CREATE INDEX idx_vehicle_user ON vehicle(user_id);
CREATE INDEX idx_notification_user ON notification(user_id);
CREATE INDEX idx_fuel_recharge_vehicle ON fuel_recharge(vehicle_id);
CREATE INDEX idx_maintenance_vehicle ON maintenance(vehicle_id);
CREATE INDEX idx_trip_driver ON trip(driver_id);
CREATE INDEX idx_trip_vehicle ON trip(vehicle_id);
CREATE INDEX idx_driver_vehicle_driver ON driver_vehicle(driver_id);
CREATE INDEX idx_driver_vehicle_vehicle ON driver_vehicle(vehicle_id);
CREATE INDEX idx_position_vehicle ON position(vehicle_id);
CREATE INDEX idx_position_history_vehicle ON position_history(vehicle_id);

-- Indexes pour les timestamps
CREATE INDEX idx_position_time ON position(position_date_time);
CREATE INDEX idx_position_history_date ON position_history(position_date_time);
CREATE INDEX idx_trip_departure_time ON trip(departure_date_time);
CREATE INDEX idx_fuel_recharge_datetime ON fuel_recharge(recharge_date_time);
CREATE INDEX idx_maintenance_datetime ON maintenance(maintenance_date_time);

-- Fonction trigger pour mettre à jour automatiquement updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers pour les tables avec updated_at
CREATE TRIGGER update_user_updated_at BEFORE UPDATE ON "user"
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_driver_updated_at BEFORE UPDATE ON driver
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_vehicle_updated_at BEFORE UPDATE ON vehicle
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();