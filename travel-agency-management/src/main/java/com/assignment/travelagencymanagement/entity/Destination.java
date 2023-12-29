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
@Table(name = "destination", uniqueConstraints = { @UniqueConstraint(name = "UniqueDestinationName", columnNames = { "name" }) })
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "travel_package_id", nullable=false)
    private TravelPackage travelPackage;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Activity> activities = new ArrayList<>();
}
