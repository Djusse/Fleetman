-- Indexes pour améliorer les performances des requêtes spatiales
CREATE INDEX IF NOT EXISTS idx_fuel_recharge_point ON fuel_recharge USING GIST(recharge_point);
CREATE INDEX IF NOT EXISTS idx_maintenance_point ON maintenance USING GIST(maintenance_point);
CREATE INDEX IF NOT EXISTS idx_trip_departure ON trip USING GIST(departure_point);
CREATE INDEX IF NOT EXISTS idx_trip_arrival ON trip USING GIST(arrival_point);
CREATE INDEX IF NOT EXISTS idx_position_coordinate ON position USING GIST(coordinate);
CREATE INDEX IF NOT EXISTS idx_position_history_linestring ON position_history USING GIST(summary_coordinate);

-- Indexes pour les clés étrangères fréquemment utilisées
CREATE INDEX IF NOT EXISTS idx_vehicle_user ON vehicle(user_id);
CREATE INDEX IF NOT EXISTS idx_notification_user ON notification(user_id);
CREATE INDEX IF NOT EXISTS idx_fuel_recharge_vehicle ON fuel_recharge(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_maintenance_vehicle ON maintenance(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_trip_driver ON trip(driver_id);
CREATE INDEX IF NOT EXISTS idx_trip_vehicle ON trip(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_driver_vehicle_driver ON driver_vehicle(driver_id);
CREATE INDEX IF NOT EXISTS idx_driver_vehicle_vehicle ON driver_vehicle(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_position_vehicle ON position(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_position_history_vehicle ON position_history(vehicle_id);

-- Indexes pour les timestamps
CREATE INDEX IF NOT EXISTS idx_position_time ON position(position_date_time);
CREATE INDEX IF NOT EXISTS idx_position_history_date ON position_history(position_date_time);
CREATE INDEX IF NOT EXISTS idx_trip_departure_time ON trip(departure_date_time);
CREATE INDEX IF NOT EXISTS idx_fuel_recharge_datetime ON fuel_recharge(recharge_date_time);
CREATE INDEX IF NOT EXISTS idx_maintenance_datetime ON maintenance(maintenance_date_time);