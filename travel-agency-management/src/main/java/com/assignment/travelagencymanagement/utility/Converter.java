package com.assignment.travelagencymanagement.utility;

import com.assignment.travelagencymanagement.custom.PassengerType;
import com.assignment.travelagencymanagement.dto.*;
import com.assignment.travelagencymanagement.entity.Activity;
import com.assignment.travelagencymanagement.entity.Destination;
import com.assignment.travelagencymanagement.entity.Passenger;
import com.assignment.travelagencymanagement.entity.TravelPackage;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<DestinationDTO> result;

    ///// MAP TO DTO

    public static TravelPackageDTO mapToTravelPackageDTO(TravelPackage travelPackage) {
        result = new ArrayList<>();
        travelPackage.getDestinations().forEach(destination -> {
            List<ActivityDTO> activityDTOList = destination.getActivities()
                    .stream()
                    .map(Converter::mapActivityToActivityDTO)
                    .toList();
            result.add(DestinationDTO.builder().id(destination.getId())
                    .activities(activityDTOList)
                    .name(destination.getName())
                    .build());
        });
        return TravelPackageDTO.builder()
                .id(travelPackage.getId())
                .travelPackageName(travelPackage.getName())
                .destinationDetails(result)
                .build();
    }

    public static ActivityDTO mapActivityToActivityDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .capacity(activity.getCapacity())
                .cost(activity.getCost())
                .build();
    }

    public static PassengerListDTO mapToPassengerListDTO(TravelPackage travelPackage) {
        List<PassengerInfo> passengerInfos = travelPackage.getPassengers().stream().map(passenger ->
                PassengerInfo.builder()
                        .id(passenger.getId())
                        .type(passenger.getType())
                        .passengerNumber(passenger.getPassengerNumber())
                        .name(passenger.getName())
                        .build()
        ).toList();
        return PassengerListDTO.builder()
                .travelPackageName(travelPackage.getName())
                .passengerCapacity(travelPackage.getPassengerCapacity())
                .passengerCount(travelPackage.getPassengers().size())
                .passengers(passengerInfos).build();
    }

    public static PassengerInfo mapPassengerToPassengerInfo(Passenger passenger) {
        List<ActivityDTO> activityDTOS = passenger.getActivities().stream().map(activity -> ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .destination(activity.getDestination().getName())
                .cost(switch(passenger.getType()){
                    case GOLD -> activity.getCost()*0.9;
                    case PREMIUM -> 0;
                    case STANDARD -> activity.getCost();
                    case DEFAULT -> -1;
                })
                .build()
        ).toList();

        return PassengerInfo.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .type(passenger.getType())
                .passengerNumber(passenger.getPassengerNumber())
                .balance(passenger.getType()!= PassengerType.PREMIUM ? passenger.getBalance() : -1)
                .activities(activityDTOS)
                .build();
    }

    public static ActivityDTO mapActivityToActivityPartialDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .capacity(activity.getCapacity()-activity.getPassengers().size())
                .build();
    }

    ///// MAP TO ENTITY

    public static TravelPackage mapTravelPackageDTOToTravelPackage(TravelPackageDTO travelPackageDTO) {
        return TravelPackage.builder()
                .name(travelPackageDTO.getTravelPackageName())
                .passengerCapacity(travelPackageDTO.getPassengerCapacity())
                .build();
    }

    public static Activity mapActivityDTOToActivity(ActivityDTO activityDTO) {
        return Activity.builder()
                .capacity(activityDTO.getCapacity())
                .cost(activityDTO.getCost())
                .description(activityDTO.getDescription())
                .name(activityDTO.getName())
                .build();
    }

    public static Destination mapDestinationDTOTODestination(DestinationDTO destinationDTO) {
        Destination destination = Destination.builder()
                .name(destinationDTO.getName())
                .build();
        List<Activity> activityList = destinationDTO.getActivities().stream().map(Converter::mapActivityDTOToActivity).toList();
        activityList.forEach(activity -> activity.setDestination(destination));
        destination.setActivities(activityList);
        return destination;
    }

    public static Passenger mapPassengerInfoToPassenger(PassengerInfo passengerInfo) {
        return Passenger.builder()
                .name(passengerInfo.getName())
                .type(passengerInfo.getType())
                .passengerNumber(passengerInfo.getPassengerNumber())
                .balance(passengerInfo.getBalance())
                .build();
    }



}
