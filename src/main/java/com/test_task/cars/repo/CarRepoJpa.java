package com.test_task.cars.repo;

import com.test_task.cars.entity.CarEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepoJpa extends JpaRepository<CarEntity, String> {
    @Query(value = """
        select count (c_number_plate) from t_car
        """, nativeQuery = true)
    int countAllCars();

    @Query(value = """
        select c_brand, count(c_brand) from t_car
        group by c_brand
        """, nativeQuery = true)
    List<String> countCarsByBrands();

    @Query(value = """
        select c_country, count(c_country) from t_car
        group by c_country
        """, nativeQuery = true)
    List<String> countCarsByCountry();

    @Query(value = """
        select extract(year from c_timestamp) as year, count(c_timestamp) from t_car
        group by c_timestamp
        """, nativeQuery = true)
    List<String> countCarsByYear();

    @Query(value = """
        update t_car
        set c_color = :color, c_mileage = :mileage
        where c_number_plate = :number
        """, nativeQuery = true)
    @Transactional
    @Modifying(clearAutomatically = true)
    void updateCar(@Param("number") String numberPlate, @Param("color") String color, @Param("mileage") double mileage);


}
