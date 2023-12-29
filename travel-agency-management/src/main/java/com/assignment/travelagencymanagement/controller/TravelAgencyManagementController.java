package com.assignment.travelagencymanagement.controller;

import com.assignment.travelagencymanagement.dto.*;
import com.assignment.travelagencymanagement.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelAgencyManagementController {

    @Autowired
    private TravelPackageService travelPackageService;

    //// GET MAPPING

    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Travel Agency Management Application");
    }
    @GetMapping(value = "/get-itinerary", produces = "application/json")
    public ResponseEntity<TravelPackageDTO> getItinerary(@RequestParam long travelPackageId) {
        return ResponseEntity.ok(travelPackageService.printItinerary(travelPackageId));
    }

    @GetMapping(value = "/get-passengers", produces = "application/json")
    public ResponseEntity<PassengerListDTO> getPassengers(@RequestParam long travelPackageId) {
        return ResponseEntity.ok(travelPackageService.printPassengers(travelPackageId));
    }

    @GetMapping(value = "/get-passenger-detail", produces = "application/json")
    public ResponseEntity<PassengerInfo> getPassengerDetail(@RequestParam long passengerId) {
        return ResponseEntity.ok(travelPackageService.printPassengerDetails(passengerId));
    }

    @GetMapping(value = "/get-available-activities", produces = "application/json")
    public ResponseEntity<List<ActivityDTO>> getAvailableActivities() {
        return ResponseEntity.ok(travelPackageService.printAvailableActivities());
    }

    @GetMapping(value = "/get-all-packages", produces = "application/json")
    public ResponseEntity<List<TravelPackageDTO>> getAllTravelPackages() {
        return ResponseEntity.ok(travelPackageService.getAllTravelPackages());
    }

    @GetMapping(value = "/join-activity", produces = "application/json")
    public ResponseEntity<Boolean> joinActivity(@RequestParam long passengerId, @RequestParam long activityId) {
        return ResponseEntity.ok(travelPackageService.joinActivity(passengerId, activityId));
    }

    //// POST MAPPING

    @PostMapping(value = "/new-travel-package", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> newTravelPackage(@RequestBody TravelPackageDTO travelPackage) {
        return ResponseEntity.ok(travelPackageService.createTravelPackage(travelPackage));
    }

    @PostMapping(value = "/add-destination")
    public ResponseEntity<Boolean> addDestination(@RequestParam long travelPackageId, @RequestBody DestinationDTO destination) {
        return ResponseEntity.ok(travelPackageService.addDestination(travelPackageId, destination));
    }

    @PostMapping(value = "/add-passenger")
    public ResponseEntity<Boolean> addPassenger(@RequestParam long travelPackageId, @RequestBody PassengerInfo passengerInfo) {
        return ResponseEntity.ok(travelPackageService.addPassenger(travelPackageId, passengerInfo));
    }

    @PostMapping(value = "/add-activity")
    public ResponseEntity<Boolean> addActivity(@RequestParam long destinationId, @RequestBody ActivityDTO activityDTO) {
        return ResponseEntity.ok(travelPackageService.addActivity(destinationId, activityDTO));
    }

    //// PATCH MAPPING

    @PatchMapping(value = "/update-travel-package")
    public ResponseEntity<Boolean> updateTravelPackage(@RequestBody TravelPackageDTO travelPackage) {
        return ResponseEntity.ok(travelPackageService.updateTravelPackage(travelPackage));
    }

    @PatchMapping(value = "/update-destination")
    public ResponseEntity<Boolean> updateDestination(@RequestBody DestinationDTO destination) {
        return ResponseEntity.ok(travelPackageService.updateDestination(destination));
    }

    @PatchMapping(value = "/update-passenger")
    public ResponseEntity<Boolean> updatePassenger(@RequestBody PassengerInfo passengerInfo) {
        return ResponseEntity.ok(travelPackageService.updatePassenger(passengerInfo));
    }

    @PatchMapping(value = "/update-activity")
    public ResponseEntity<Boolean> updateActivity(@RequestBody ActivityDTO activityDTO) {
        return ResponseEntity.ok(travelPackageService.updateActivity(activityDTO));
    }

    //// PUT MAPPING

    @PutMapping(value = "/replace-travel-package")
    public ResponseEntity<Boolean> replaceTravelPackage(@RequestBody TravelPackageDTO travelPackage) {
        return ResponseEntity.ok(travelPackageService.replaceTravelPackage(travelPackage));
    }

    @PutMapping(value = "/replace-destination")
    public ResponseEntity<Boolean> replaceDestination(@RequestBody DestinationDTO destination) {
        return ResponseEntity.ok(travelPackageService.replaceDestination(destination));
    }

    @PutMapping(value = "/replace-passenger")
    public ResponseEntity<Boolean> replacePassenger(@RequestBody PassengerInfo passengerInfo) {
        return ResponseEntity.ok(travelPackageService.replacePassenger(passengerInfo));
    }

    @PutMapping(value = "/replace-activity")
    public ResponseEntity<Boolean> replaceActivity(@RequestBody ActivityDTO activityDTO) {
        return ResponseEntity.ok(travelPackageService.replaceActivity(activityDTO));
    }

    //// DELETE MAPPING

    @DeleteMapping(value = "/delete-travel-package")
    public ResponseEntity<Boolean> deleteTravelPackage(@RequestParam long travelPackageId) {
        return ResponseEntity.ok(travelPackageService.removeTravelPackage(travelPackageId));
    }

    @DeleteMapping(value = "/delete-destination")
    public ResponseEntity<Boolean> deleteDestination(@RequestParam long travelPackageId, @RequestParam long destinationId) {
        return ResponseEntity.ok(travelPackageService.removeDestination(travelPackageId, destinationId));
    }

    @DeleteMapping(value = "/delete-passenger")
    public ResponseEntity<Boolean> deletePassenger(@RequestParam long travelPackageId, @RequestParam long passengerId) {
        return ResponseEntity.ok(travelPackageService.removePassenger(travelPackageId, passengerId));
    }

    @DeleteMapping(value = "/delete-activity")
    public ResponseEntity<Boolean> deleteActivity(@RequestParam long destinationId, @RequestParam long activityId) {
        return ResponseEntity.ok(travelPackageService.removeActivity(destinationId, activityId));
    }



}
