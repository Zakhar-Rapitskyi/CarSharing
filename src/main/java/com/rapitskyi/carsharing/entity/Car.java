package com.rapitskyi.carsharing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "renter_id")
    User renter;

    @OneToOne
    @JoinColumn(name = "image_id")
    @JsonIgnore
    ImageData imageData;

    @Column(name = "rented")
    Boolean isRented;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    List<Booking> bookings = Collections.emptyList();

    @Column(name = "vendor")
    String vendor;

    @Column(name = "model")
    String model;

    @Column(name = "year")
    Integer year;

    @Column(name = "color")
    String color;

    @Column(name = "location")
    String location;

    @Column(name = "description")
    String description;

    @Column(name = "image_url")
    String ImageURL;

}
