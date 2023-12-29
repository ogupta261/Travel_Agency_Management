package com.assignment.travelagencymanagement.utility;

import com.assignment.travelagencymanagement.entity.Activity;
import com.assignment.travelagencymanagement.entity.Destination;
import com.assignment.travelagencymanagement.entity.Passenger;
import com.assignment.travelagencymanagement.exception.CustomException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Validation {

    public static String INVALID_TRAVEL_PACKAGE = "NOT FOUND! Invalid Travel Package ID!";
    public static String TRAVEL_PACKAGE_NOT_JOINED = "FULL! This Travel Package is full!";
    public static String INVALID_PASSENGER = "NOT FOUND! Invalid Passenger ID!";
    public static String INVALID_DESTINATION = "NOT FOUND! Invalid Destination ID!";
    public static String INVALID_ACTIVTY = "NOT FOUND! Invalid Activity ID!";
    public static String ACTIVITY_NOT_JOINED = "FULL! This activity is full!";
    public static String INSUFFICIENT_BALANCE = "LOW BALANCE! Your balance is low!";
    public static String DESTINATION_EXISTS = "DUPLICATE! This destination already exists!";
    public static String ALREADY_PARTICIPATED = "DUPLICATE! Passenger has already participated in the activity!";

    public static void checkActivitySpace(Activity activity) {
        if(activity.getCapacity() <= activity.getPassengers().size()) throw new CustomException(ACTIVITY_NOT_JOINED);
    }

    public static void checkDestinationByName(List<Destination> destinations, String destinationName) {
        Optional<Destination> destination_found = destinations.stream().filter(destination -> Objects.equals(destination.getName(), destinationName)).findAny();
        if(destination_found.isPresent()) throw new CustomException(DESTINATION_EXISTS);
    }

    public static void checkExistingParticipation(String activityName, Passenger passenger) {
        if(passenger.getActivities().stream().anyMatch(activity -> Objects.equals(activity.getName(), activityName))) throw new CustomException(ALREADY_PARTICIPATED);
    }

}
