package com.rapitskyi.carsharing.service;

import com.rapitskyi.carsharing.entity.Booking;
import com.rapitskyi.carsharing.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;

    public void save(Booking booking){
        bookingRepository.save(booking);
    }

    public void deleteById(Long id){
        bookingRepository.deleteById(id);
    }
    public List<Booking> findAllByRenterId(Long id){return bookingRepository.findAllByRenterId(id);}
}
