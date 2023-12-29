package com.assignment.travelagencymanagement.service;

import com.assignment.travelagencymanagement.custom.PassengerType;
import com.assignment.travelagencymanagement.dao.ActivityRepo;
import com.assignment.travelagencymanagement.dao.DestinationRepo;
import com.assignment.travelagencymanagement.dao.PassengerRepo;
import com.assignment.travelagencymanagement.dao.TravelPackageRepo;
import com.assignment.travelagencymanagement.dto.*;
import com.assignment.travelagencymanagement.entity.Activity;
import com.assignment.travelagencymanagement.entity.Destination;
import com.assignment.travelagencymanagement.entity.Passenger;
import com.assignment.travelagencymanagement.entity.TravelPackage;
import com.assignment.travelagencymanagement.exception.CustomException;

import static com.assignment.travelagencymanagement.utility.Converter.mapTravelPackageDTOToTravelPackage;
import static com.assignment.travelagencymanagement.utility.Validation.*;

import static com.assignment.travelagencymanagement.utility.Converter.*;

import com.assignment.travelagencymanagement.utility.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelPackageServiceImpl implements TravelPackageService{

    @Autowired
    private TravelPackageRepo travelPackageRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private DestinationRepo destinationRepo;

    @Autowired
    private PassengerRepo passengerRepo;

    @Override
    public TravelPackageDTO printItinerary(long travelPackageId) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);
        return mapToTravelPackageDTO(travelPackageById.get());
    }

    @Override
    public PassengerListDTO printPassengers(long travelPackageId) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);
        return mapToPassengerListDTO(travelPackageById.get());
    }

    @Override
    public PassengerInfo printPassengerDetails(long passengerId) {
        Optional<Passenger> passengerById = passengerRepo.findById(passengerId);
        if(passengerById.isEmpty()) throw new CustomException(INVALID_PASSENGER);
        return mapPassengerToPassengerInfo(passengerById.get());
    }

    @Override
    public List<ActivityDTO> printAvailableActivities() {
        return activityRepo.findAll().stream()
                .filter(activity -> activity.getCapacity()-activity.getPassengers().size()>0)
                .map(Converter::mapActivityToActivityPartialDTO)
                .toList();
    }

    @Override
    public boolean createTravelPackage(TravelPackageDTO travelPackageDTO) {
        TravelPackage travelPackage = mapTravelPackageDTOToTravelPackage(travelPackageDTO);

        List<Destination> destinationList = travelPackageDTO.getDestinationDetails()
                .stream()
                .map(Converter::mapDestinationDTOTODestination)
                .toList();
        destinationList.forEach(destination -> destination.setTravelPackage(travelPackage));
        travelPackage.setDestinations(destinationList);
        travelPackageRepo.save(travelPackage);
        return true;
    }

    @Override
    public boolean replaceTravelPackage(TravelPackageDTO travelPackageDTO) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageDTO.getId());
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);
        TravelPackage travelPackage = travelPackageById.get();
        TravelPackage travelPackageFromDTO = mapTravelPackageDTOToTravelPackage(travelPackageDTO);

        List<Destination> destinationList = travelPackageDTO.getDestinationDetails()
                .stream()
                .map(Converter::mapDestinationDTOTODestination)
                .toList();
        destinationList.forEach(destination -> destination.setTravelPackage(travelPackageFromDTO));
        travelPackageFromDTO.setDestinations(destinationList);
        travelPackageFromDTO.setId(travelPackage.getId());
        travelPackageFromDTO.setPassengers(travelPackage.getPassengers());

        travelPackageRepo.save(travelPackageFromDTO);

        return true;
    }

    @Override
    public boolean updateTravelPackage(TravelPackageDTO travelPackageDTO) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageDTO.getId());
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);
        TravelPackage travelPackage = travelPackageById.get();

        travelPackage.setPassengerCapacity(
                travelPackageDTO.getPassengerCapacity() != -1
                        ? travelPackageDTO.getPassengerCapacity()
                        : travelPackage.getPassengerCapacity()
        );
        travelPackage.setDestinations(
                travelPackageDTO.getDestinationDetails().size() != 0
                        ? travelPackageDTO.getDestinationDetails()
                            .stream()
                            .map(Converter::mapDestinationDTOTODestination)
                            .toList()
                        : travelPackage.getDestinations());

        travelPackageRepo.save(travelPackage);

        return true;
    }

    public boolean removeTravelPackage(long travelPackageId) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);
        travelPackageRepo.delete(travelPackageById.get());
        return true;
    }

    @Override
    public List<TravelPackageDTO> getAllTravelPackages() {
        return travelPackageRepo.findAll().stream().map(Converter::mapToTravelPackageDTO).toList();
    }

    @Override
    public boolean addDestination(long travelPackageId, DestinationDTO destinationDTO) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);
        TravelPackage travelPackage = travelPackageById.get();
        List<Destination> destinations = travelPackage.getDestinations();

        checkDestinationByName(destinations, destinationDTO.getName());

        Destination destination_ = mapDestinationDTOTODestination(destinationDTO);
        destination_.setTravelPackage(travelPackage);
        destinations.add(destination_);

        travelPackageRepo.save(travelPackage);
        return true;
    }

    @Override
    public boolean replaceDestination(DestinationDTO destinationDTO) {
        Optional<Destination> destinationById = destinationRepo.findById(destinationDTO.getId());
        if(destinationById.isEmpty()) throw new CustomException(INVALID_DESTINATION);

        Destination destination = destinationById.get();
        Destination destinationFromDTO = mapDestinationDTOTODestination(destinationDTO);
        TravelPackage travelPackage = destination.getTravelPackage();

        destinationFromDTO.setTravelPackage(travelPackage);

        List<Destination> destinations = travelPackage.getDestinations();
        destinations.remove(destination);
        destinations.add(destinationFromDTO);

        destinationRepo.save(destination);
        return true;
    }

    @Override
    public boolean updateDestination(DestinationDTO destinationDTO) {
        Optional<Destination> destinationById = destinationRepo.findById(destinationDTO.getId());
        if(destinationById.isEmpty()) throw new CustomException(INVALID_DESTINATION);

        Destination destination = destinationById.get();


        destinationRepo.save(destination);
        return true;
    }

    @Override
    public boolean removeDestination(long travelPackageId, long destinationId) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        Optional<Destination> destinationById = destinationRepo.findById(destinationId);

        if(destinationById.isEmpty()) throw new CustomException(INVALID_DESTINATION);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);

        TravelPackage travelPackage = travelPackageById.get();
        travelPackage.getDestinations().remove(destinationById.get());
        travelPackageRepo.save(travelPackage);

        return true;
    }

    @Override
    public boolean addPassenger(long travelPackageId, PassengerInfo passengerInfo) {
        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);

        TravelPackage travelPackage = travelPackageById.get();

        if(travelPackage.getPassengerCapacity()-travelPackage.getPassengers().size()<=0) throw new CustomException(TRAVEL_PACKAGE_NOT_JOINED);

        Passenger passenger = mapPassengerInfoToPassenger(passengerInfo);
        passenger.setTravelPackage(travelPackage);
        travelPackage.getPassengers().add(passenger);

        travelPackageRepo.save(travelPackage);
        return true;
    }

    @Override
    public boolean replacePassenger(PassengerInfo passengerInfo) {

        Optional<Passenger> passengerById = passengerRepo.findById(passengerInfo.getId());
        if(passengerById.isEmpty()) throw new CustomException(INVALID_PASSENGER);

        Passenger passenger = passengerById.get();
        Passenger passengerFromInfo = mapPassengerInfoToPassenger(passengerInfo);
        passengerFromInfo.setId(passengerInfo.getId());
        passengerFromInfo.setTravelPackage(passenger.getTravelPackage());
//
//        List<Activity> oldActivities = passenger.getActivities();
//        oldActivities.forEach(activity -> activity.getPassengers().remove(passenger));

        List<Activity> newActivityList = passengerInfo.getActivities().stream().map(
                activityDTO -> {
                    Optional<Activity> activityById = activityRepo.findById(activityDTO.getId());
                    return activityById.orElseGet(() -> Activity.builder().id((long) -1).build());
                }
        ).filter(activity -> activity.getId()!=-1).toList();

        passengerFromInfo.setActivities(newActivityList);

        passengerRepo.save(passengerFromInfo);

        return true;
    }

    @Override
    public boolean updatePassenger(PassengerInfo passengerInfo) {
        Optional<Passenger> passengerById = passengerRepo.findById(passengerInfo.getId());
        if(passengerById.isEmpty()) throw new CustomException(INVALID_PASSENGER);

        Passenger passenger = passengerById.get();
        passenger.setPassengerNumber(passengerInfo.getPassengerNumber() != -1 ? passengerInfo.getPassengerNumber() : passenger.getPassengerNumber());
        passenger.setType(passengerInfo.getType().equals(PassengerType.DEFAULT) ? passenger.getType() : passengerInfo.getType());
        passenger.setBalance(passengerInfo.getBalance() != -1 ? passengerInfo.getBalance() : passenger.getBalance());

        if(passengerInfo.getActivities().size()>0) {
//
//            List<Activity> oldActivities = passenger.getActivities();
//            oldActivities.forEach(activity -> activity.getPassengers().remove(passenger));

            List<Activity> newActivityList = passengerInfo.getActivities().stream().map(
                    activityDTO -> {
                        Optional<Activity> activityById = activityRepo.findById(activityDTO.getId());
                        return activityById.orElseGet(() -> Activity.builder().id((long) -1).build());
                    }
            ).filter(activity -> activity.getId()!=-1).toList();

//            newActivityList.forEach(activity -> {
//                List<Passenger> passengers = activity.getPassengers();
//                passengers.add(passenger);
//                activity.setPassengers(passengers);
//            });
            passenger.setActivities(newActivityList);
        }

        passengerRepo.save(passenger);

        return true;
    }

    @Override
    public boolean removePassenger(long travelPackageId, long passengerId) {

        Optional<TravelPackage> travelPackageById = travelPackageRepo.findById(travelPackageId);
        Optional<Passenger> passengerById = passengerRepo.findById(passengerId);

        if(passengerById.isEmpty()) throw new CustomException(INVALID_PASSENGER);
        if(travelPackageById.isEmpty()) throw new CustomException(INVALID_TRAVEL_PACKAGE);

        TravelPackage travelPackage = travelPackageById.get();
        travelPackage.getPassengers().remove(passengerById.get());
        travelPackageRepo.save(travelPackage);

        return true;
    }

    @Override
    public boolean addActivity(long destinationId, ActivityDTO activityDTO) {

        Optional<Destination> destinationById = destinationRepo.findById(destinationId);
        if(destinationById.isEmpty()) throw new CustomException(INVALID_DESTINATION);

        Activity activity = mapActivityDTOToActivity(activityDTO);
        Destination destination = destinationById.get();
        activity.setDestination(destination);
        destination.getActivities().add(activity);

        destinationRepo.save(destination);

        return true;
    }

    @Override
    public boolean replaceActivity(ActivityDTO activityDTO) {
        Optional<Activity> activityById = activityRepo.findById(activityDTO.getId());
        if(activityById.isEmpty()) throw new CustomException(INVALID_ACTIVTY);

        Activity activity = activityById.get();
        Activity activityFromDTO = mapActivityDTOToActivity(activityDTO);
        activityFromDTO.setDestination(destinationRepo.findByName(activityDTO.getDestination()));
        activityFromDTO.setPassengers(activity.getPassengers());
        activityFromDTO.setId(activity.getId());

        activityRepo.save(activityFromDTO);
        return true;
    }

    @Override
    public boolean updateActivity(ActivityDTO activityDTO) {

        return true;
    }

    @Override
    public boolean removeActivity(long destinationId, long activityId) {

        Optional<Destination> destinationById = destinationRepo.findById(destinationId);
        Optional<Activity> activityById = activityRepo.findById(activityId);

        if(activityById.isEmpty()) throw new CustomException(INVALID_ACTIVTY);
        if(destinationById.isEmpty()) throw new CustomException(INVALID_DESTINATION);

        Destination destination = destinationById.get();
        destination.getActivities().remove(activityById.get());
        destinationRepo.save(destination);

        return true;
    }

    @Override
    public boolean joinActivity(long passengerId, long activityId) {

        Optional<Activity> activityById = activityRepo.findById(activityId);
        Optional<Passenger> passengerById = passengerRepo.findById(passengerId);

        if(passengerById.isEmpty()) throw new CustomException(INVALID_PASSENGER);
        if(activityById.isEmpty()) throw new CustomException(INVALID_ACTIVTY);

        Activity activity = activityById.get();
        Passenger passenger = passengerById.get();

        checkExistingParticipation(activity.getName(),passenger);
        checkActivitySpace(activity);
        double costToPay = passenger.getType() == PassengerType.PREMIUM ? -1 : passenger.getType() == PassengerType.GOLD ? activity.getCost()*0.9 : activity.getCost();

        double balance = passenger.getBalance();
        if(costToPay != -1 && balance<costToPay) throw new CustomException(INSUFFICIENT_BALANCE);
        if(costToPay != -1) passenger.setBalance(balance-costToPay);

        activity.getPassengers().add(passenger);
        passenger.getActivities().add(activity);

        activityRepo.save(activity);

        return true;
    }
}
