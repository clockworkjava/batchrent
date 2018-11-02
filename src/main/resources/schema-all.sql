DROP TABLE sunny_ride_rent IF EXISTS;

CREATE TABLE sunny_ride_rent (
    car_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    make VARCHAR(20),
    model VARCHAR(20),
    cost FLOAT(5)
);


INSERT INTO sunny_ride_rent values (1,'skoda','octavia',32.0)
