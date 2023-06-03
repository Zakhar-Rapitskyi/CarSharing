package com.rapitskyi.carsharing.schedule;

import com.rapitskyi.carsharing.entity.Booking;
import com.rapitskyi.carsharing.entity.Car;
import com.rapitskyi.carsharing.service.BookingService;
import com.rapitskyi.carsharing.service.CarService;
import com.rapitskyi.carsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
public class BookingTimeSchedule {
    @Autowired
    private CarService carService;
    @Autowired

    private UserService userService;
    @Autowired

    private BookingService bookingService;

    @Transactional
    @Scheduled(fixedRate = 10000) // перевіряти кожні 10 сек
    public void checkRentedCars() {


        //перевірка чи незаброньовані машини треба змінювати
        List<Car> allCars = carService.findAll();
        Car car;

        OUTER:
        for (int i=0;i<allCars.size();i++) {
            car=allCars.get(i);
            List<Booking> bookings = car.getBookings();
            LocalDateTime now = LocalDateTime.now();

            Iterator<Booking> iterator = bookings.iterator();
            while (iterator.hasNext()) {
                Booking booking = iterator.next();
                if (booking.getEndTime().isBefore(now)) {
                    iterator.remove();
                    bookingService.deleteById(booking.getId()); // Видалити бронювання з бази даних
                }
            }

            if(car.getIsRented()) {
                for (Booking booking : bookings) {
                    if (booking.getStartTime().isBefore(now)) { //зараз після start_date
                        continue OUTER;
                    }
                }
                car.setIsRented(false);
                car.setRenter(null);
                carService.save(car);
            }
            else {
                for (Booking booking : bookings) {
                    if (booking.getStartTime().isBefore(now)) { //зараз після start_date
                        car.setIsRented(true);
                        car.setRenter(userService.findUserById(booking.getRenterId()));
                        carService.save(car);
                        continue OUTER;
                    }
                }
            }
        }
    }
}

