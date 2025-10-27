package com.polytechnique.fleetman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverVehicleId implements Serializable {

    private Long driver;  // ← Correspond au nom du champ dans DriverVehicleEntity
    private Long vehicle; // ← Correspond au nom du champ dans DriverVehicleEntity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverVehicleId that = (DriverVehicleId) o;
        return Objects.equals(driver, that.driver) &&
                Objects.equals(vehicle, that.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, vehicle);
    }
}