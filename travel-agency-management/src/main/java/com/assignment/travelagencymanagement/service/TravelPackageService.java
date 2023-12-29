package com.assignment.travelagencymanagement.service;

import com.assignment.travelagencymanagement.dto.*;

import java.util.List;

public interface TravelPackageService {

    TravelPackageDTO printItinerary(long travelPackageId);

    PassengerListDTO printPassengers(long travelPackageId);

    PassengerInfo printPassengerDetails(long passengerId);

    List<ActivityDTO> printAvailableActivities();

    boolean createTravelPackage(TravelPackageDTO travelPackageDTO);

    boolean replaceTravelPackage(TravelPackageDTO travelPackage);

    boolean updateTravelPackage(TravelPackageDTO travelPackage);

    boolean removeTravelPackage(long travelPackageId);

    List<TravelPackageDTO> getAllTravelPackages();

    boolean addDestination(long travelPackageId, DestinationDTO destination);

    boolean replaceDestination(DestinationDTO destination);

    boolean updateDestination(DestinationDTO destination);

    boolean removeDestination(long travelPackageId, long destinationId);

    boolean addPassenger(long travelPackageId, PassengerInfo passengerInfo);

    boolean replacePassenger(PassengerInfo passengerInfo);

    boolean updatePassenger(PassengerInfo passengerInfo);

    boolean removePassenger(long travelPackageId, long passengerId);

    boolean addActivity(long destinationId, ActivityDTO activityDTO);

    boolean replaceActivity(ActivityDTO activityDTO);

    boolean updateActivity(ActivityDTO activityDTO);

    boolean removeActivity(long destinationId, long activityId);

    boolean joinActivity(long passengerId, long activityId);
}
