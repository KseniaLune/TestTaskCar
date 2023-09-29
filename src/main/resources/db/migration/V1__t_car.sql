create table t_car (
    c_number_plate varchar(10) primary key,
    c_brand varchar(100),
    c_color varchar(100),
    c_year_of_manufacture int,
    c_country varchar(20),
    c_trunk_volume double precision,
    c_mileage double precision,
    c_timestamp timestamp default current_timestamp
);