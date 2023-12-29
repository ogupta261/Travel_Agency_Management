package com.assignment.travelagencymanagement.dao;

import com.assignment.travelagencymanagement.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {
}
