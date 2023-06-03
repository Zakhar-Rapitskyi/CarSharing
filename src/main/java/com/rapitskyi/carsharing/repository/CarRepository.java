package com.rapitskyi.carsharing.repository;

import com.rapitskyi.carsharing.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findAllByIdIsIn(List<Long> ids);
}
