package com.rapitskyi.carsharing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "email", length = 319,nullable = false,unique = true)
    private String email;

    @Column(name = "password", length = 70,nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "first_name", length = 20,nullable = false)
    private String firstname;

    @Column(name = "last_name", length = 20,nullable = false)
    private String lastname;

    @Column(name = "phone", length = 13,unique = true)
    private String phone;

    @OneToOne
    @JoinColumn(name = "image_id")
    @JsonIgnore
    private ImageData imageData;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Car> ownedCars;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "renter", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Car> rentedCars;

    @Column(name = "image_url")
    private String ImageURL;
    @Column(name = "is_volunteer")
    private boolean isVolunteer;
}
