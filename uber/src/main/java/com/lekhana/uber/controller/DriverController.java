package com.lekhana.uber.controller;

import com.lekhana.uber.model.Ride;
import com.lekhana.uber.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/driver")
@PreAuthorize("hasRole('DRIVER')")
public class DriverController {
    private final RideService rideService;

    public DriverController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping("/rides/requests")
    public ResponseEntity<List<Ride>> getPendingRides() {
        List<Ride> rides = rideService.getPendingRides();
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/rides/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId) {
        Ride ride = rideService.acceptRide(rideId);
        return ResponseEntity.ok(ride);
    }
}
