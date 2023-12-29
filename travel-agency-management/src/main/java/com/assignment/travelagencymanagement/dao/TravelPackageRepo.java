package com.assignment.travelagencymanagement.dao;

import com.assignment.travelagencymanagement.entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPackageRepo extends JpaRepository<TravelPackage, Long> {
}
