package com.lekhana.uber.controller;

import com.lekhana.uber.dto.CreateRideRequest;
import com.lekhana.uber.model.Ride;
import com.lekhana.uber.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest request) {
        Ride ride = rideService.createRide(request);
        return ResponseEntity.ok(ride);
    }

    @PostMapping("/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        Ride ride = rideService.completeRide(rideId);
        return ResponseEntity.ok(ride);
    }
}
