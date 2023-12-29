package com.assignment.travelagencymanagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "travel_package")
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String name;

    private int passengerCapacity;

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Destination> destinations = new ArrayList<>();

    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Passenger> passengers = new ArrayList<>();
}
