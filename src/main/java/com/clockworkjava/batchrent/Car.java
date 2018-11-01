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

    private String mark;
    private String model;
    private BigDecimal rentingCostPerHour;

    public Car() {
    }

    public Car(String mark, String model, BigDecimal rentingCostPerHour) {
        this.mark = mark;
        this.model = model;
        this.rentingCostPerHour = rentingCostPerHour;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
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
                Objects.equals(mark, car.mark) &&
                Objects.equals(model, car.model) &&
                Objects.equals(rentingCostPerHour, car.rentingCostPerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, model, rentingCostPerHour);
    }
}
