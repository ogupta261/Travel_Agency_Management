package com.assignment.travelagencymanagement.entity;

import com.assignment.travelagencymanagement.custom.PassengerType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "travel_package_id", nullable=false)
    private TravelPackage travelPackage;

    private int passengerNumber;

    @Enumerated(EnumType.STRING)
    private PassengerType type;

    @Nullable
    private double balance;

    @ManyToMany(mappedBy = "passengers", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();
}
