package com.clockworkjava.batchrent;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        BigDecimal cost = resultSet.getBigDecimal("cost");

        return new Car(make,model,cost);
    }
}
