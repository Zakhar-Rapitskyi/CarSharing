package com.rapitskyi.carsharing.service;

import com.rapitskyi.carsharing.entity.Booking;
import com.rapitskyi.carsharing.entity.Car;
import com.rapitskyi.carsharing.exception.BadRequestException;
import com.rapitskyi.carsharing.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService{
    private final CarRepository carRepository;

    public List<Car> findAll(){return carRepository.findAll();}
    public Car findById(Long id){return carRepository.findById(id).orElseThrow(
            ()->new BadRequestException("There is no car with id " + id));}
    public boolean isCarAvailable(Car car, LocalDateTime startDate, LocalDateTime endDate) {
        for (Booking booking : car.getBookings()) {
            if ((startDate.equals(booking.getStartTime()))
                    ||(endDate.equals(booking.getEndTime()))
                    ||(startDate.isAfter(booking.getStartTime())&&startDate.isBefore(booking.getEndTime()))
                    || (endDate.isAfter(booking.getStartTime()) && endDate.isBefore(booking.getEndTime()))
                    ||(booking.getStartTime().isAfter(startDate)&&booking.getEndTime().isBefore(startDate))
                    || (booking.getStartTime().isAfter(endDate) && booking.getEndTime().isBefore(endDate))) {
                return false;
            }
        }
        return true;
    }
    public Car save(Car car){
        return carRepository.save(car);
    }
    public List<Car> findAllByIdIsIn(List<Long> ids){return carRepository.findAllByIdIsIn(ids);}
}
