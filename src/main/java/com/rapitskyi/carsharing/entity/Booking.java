package com.rapitskyi.carsharing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @JsonIgnore
    @Column(name="car_id")
    private Long carId;

    @JsonIgnore
    @Column(name="renter_id")
    private Long renterId;

    @NotNull
    @Column(name="start_time")
    private LocalDateTime startTime;

    @NotNull
    @Column(name="end_time")
    private LocalDateTime endTime;

    public Booking(Long carId, Long renterId, LocalDateTime startTime, LocalDateTime endTime) {
        this.carId = carId;
        this.renterId = renterId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
