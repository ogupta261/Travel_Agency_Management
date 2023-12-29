package com.assignment.travelagencymanagement.dao;

import com.assignment.travelagencymanagement.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepo extends JpaRepository<Destination, Long> {

    @Query(value = "select d FROM Destination d WHERE LOWER(d.name)=LOWER(:name)")
    Destination findByName(String name);
}
