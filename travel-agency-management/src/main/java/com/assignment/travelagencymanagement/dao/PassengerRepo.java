package com.assignment.travelagencymanagement.dao;

import com.assignment.travelagencymanagement.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger, Long> {
}
