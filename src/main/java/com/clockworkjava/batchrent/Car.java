package com.clockworkjava.batchrent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String make;
    private String model;
    private BigDecimal rentingCostPerHour;

    public Car() {
    }

    public Car(String make, String model, BigDecimal rentingCostPerHour) {
        this.make = make;
        this.model = model;
        this.rentingCostPerHour = rentingCostPerHour;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + make + '\'' +
                ", model='" + model + '\'' +
                ", rentingCostPerHour=" + rentingCostPerHour +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                Objects.equals(make, car.make) &&
                Objects.equals(model, car.model) &&
                Objects.equals(rentingCostPerHour, car.rentingCostPerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, rentingCostPerHour);
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getRentingCostPerHour() {
        return rentingCostPerHour;
    }

    public void setRentingCostPerHour(BigDecimal rentingCostPerHour) {
        this.rentingCostPerHour = rentingCostPerHour;
    }
}
