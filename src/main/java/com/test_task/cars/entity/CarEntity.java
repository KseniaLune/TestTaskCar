package com.test_task.cars.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_car")
@Builder
public class CarEntity {
    @Id
    @Column(name = "c_number_plate")
    private String numberPlate;
    @Column(name = "c_brand")
    private String brand;
    @Column(name = "c_color")
    private String color;
    @Column(name = "c_year_of_manufacture")
    private Integer yearOfManufacture;
    @Column(name = "c_country")
    private String country;
    @Column(name = "c_trunk_volume")
    private Double trunkVolume;
    @Column(name = "c_mileage")
    private Double mileage;
    @Column(name = "c_timestamp")
    private LocalDateTime time;
}
