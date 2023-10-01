package com.test_task.cars.repo;

import com.test_task.cars.entity.CarEntity;
import com.test_task.cars.model.SortOf;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepoHibernate {
    private final EntityManager entityManager;

    public List<CarEntity> getCarsWithFilter(
        String brand, String color, int yearOfManufacture, String country,
        int mileageFrom, int mileageTo,
        int trunkVolumeFrom, int trunkVolumeTo, Optional<SortOf> sortParam
    ) {
        StringBuilder rowQuery = new StringBuilder("select * from t_car");
        boolean notFirstCondition = false;

        if (color != null || brand != null || yearOfManufacture != 0 || country != null) {
            rowQuery.append(" where ");

            if (color != null) {
                rowQuery.append("c_color = :color");
                notFirstCondition = true;
            }

            if (brand != null) {
                if (notFirstCondition) {
                    rowQuery.append(" and ");
                } else {
                    notFirstCondition = true;
                }

                rowQuery.append("c_brand = :brand");
            }

            if (yearOfManufacture != 0) {
                if (notFirstCondition) {
                    rowQuery.append(" and ");
                } else {
                    notFirstCondition = true;
                }

                rowQuery.append("c_year_of_manufacture = :year");
            }

            if (country != null) {
                if (notFirstCondition) {
                    rowQuery.append(" and ");
                } else {
                    notFirstCondition = true;
                }

                rowQuery.append("c_country = :country");
            }
        }

        if (mileageFrom != 0 || mileageTo != 0) {
            if (notFirstCondition) {
                rowQuery.append(" and ");
            } else {
                rowQuery.append(" where ");
                notFirstCondition = true;
            }

            if (mileageTo == 0) { //from left to max
                rowQuery.append("c_mileage >= :mileage_from");
            } else {
                rowQuery.append("c_mileage between :mileage_from and :mileage_to");
            }
        }

        if (trunkVolumeFrom != 0 || trunkVolumeTo != 0) {
            if (notFirstCondition) {
                rowQuery.append(" and ");
            } else {
                rowQuery.append(" where ");
                notFirstCondition = true;
            }

            if (trunkVolumeTo == 0) { //from left to max
                rowQuery.append("c_trunk_volume >= :trunk_volume_from");
            } else {
                rowQuery.append("c_trunk_volume between :trunk_volume_from and :trunk_volume_to");
            }
        }

        if (sortParam.isPresent()) {
            rowQuery.append(" order by ");
            rowQuery.append("c_" + sortParam.get().name);
        }

        Query query = entityManager.createNativeQuery(rowQuery.toString(), CarEntity.class);

        if (color != null) {
            query.setParameter("color", color);
        }

        if (brand != null) {
            query.setParameter("brand", brand);
        }

        if (yearOfManufacture != 0) {
            query.setParameter("year", yearOfManufacture);
        }

        if (country != null) {
            query.setParameter("country", country);
        }

        if (mileageFrom != 0 || mileageTo != 0) {
            query.setParameter("mileage_from", mileageFrom);
            if (mileageTo != 0) {
                query.setParameter("mileage_to", mileageTo);
            }
        }

        if (trunkVolumeFrom != 0 || trunkVolumeTo != 0) {
            query.setParameter("trunk_volume_from", trunkVolumeFrom);
            if (trunkVolumeTo != 0) {
                query.setParameter("trunk_volume_to", trunkVolumeTo);
            }
        }

        List<CarEntity> resultList = query.getResultList();
        return resultList;
    }
}
