package com.rapitskyi.carsharing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="car_inquiries")
public class Inquire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "volunteer_id")
    @JsonIgnore
    private User volunteer;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "description")
    private String description;

    @Column(name = "needs_delivery")
    private boolean needsDelivery;

}
