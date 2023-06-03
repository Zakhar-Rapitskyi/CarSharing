package com.rapitskyi.carsharing.controller;

import com.rapitskyi.carsharing.dto.request.AddCarRequest;
import com.rapitskyi.carsharing.dto.response.CarIdResponse;
import com.rapitskyi.carsharing.entity.Booking;
import com.rapitskyi.carsharing.entity.Car;
import com.rapitskyi.carsharing.entity.User;
import com.rapitskyi.carsharing.exception.BadRequestException;
import com.rapitskyi.carsharing.exception.ConflictException;
import com.rapitskyi.carsharing.mapper.CarMapper;
import com.rapitskyi.carsharing.service.BookingService;
import com.rapitskyi.carsharing.service.CarService;
import com.rapitskyi.carsharing.entity.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carsharing/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final BookingService bookingService;

    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        List<Car> availableCars = carService.findAll();

        return ResponseEntity.ok(availableCars.stream().
                filter(i -> i.getOwner().getId() != user.getId()).toList());
    }

    @GetMapping("/availableByTime")
    public ResponseEntity<?> getAvailableCars(@RequestParam("startTime")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                              LocalDateTime startTime,
                                              @RequestParam("endTime")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                                              LocalDateTime endTime) {

        if (startTime.isAfter(endTime)) {
            throw new BadRequestException("Start time must be before end time");
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Start time must be after now");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        List<Car> availableCars = carService.findAll().stream().
                filter(i -> carService.isCarAvailable(i, startTime, endTime)).collect(Collectors.toList());

        return ResponseEntity.ok(availableCars.stream().
                filter(i -> i.getOwner().getId() != user.getId()).toList());
    }

    @GetMapping("/owned")
    public ResponseEntity<List<Car>> getOwnedCars() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        List<Car> ownedCars = user.getOwnedCars();
        return ResponseEntity.ok(ownedCars);
    }

    @GetMapping("/rented")
    public ResponseEntity<List<Car>> getRentedCars() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        List<Long> integers = bookingService.findAllByRenterId(user.getId())
                .stream().map(i->i.getCarId()).collect(Collectors.toList());

        return ResponseEntity.ok(carService.findAllByIdIsIn(integers));
    }

    @PutMapping("/rent")
    public ResponseEntity<?> rentCar(@RequestParam("id") Long id,
                                     @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startTime,
                                     @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endTime) {

        if (startTime.isAfter(endTime)) {
            throw new BadRequestException("Start time must be before end time");
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Start time must be after now");
        }

        Car car = carService.findById(id);

        if (!carService.isCarAvailable(car, startTime, endTime)) {
            throw new ConflictException("This auto is not available for this period");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User renter = userDetails.getUser();
        if(renter.isVolunteer()==false){
            throw new BadRequestException("You are not a volunteer, you can not rent car");
        }
        User owner = car.getOwner();

        if (renter.getId().equals(owner.getId())) {
            throw new BadRequestException("You can not rent your car");
        }

        Booking booking = new Booking(car.getId(), renter.getId(), startTime, endTime);

        bookingService.save(booking);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody @Valid AddCarRequest carInfo) throws IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        Car car = CarMapper.mapToAddCar(carInfo);
        car.setOwner(user);

        car = carService.save(car);

        return ResponseEntity.ok(new CarIdResponse(car.getId()));
    }
}
