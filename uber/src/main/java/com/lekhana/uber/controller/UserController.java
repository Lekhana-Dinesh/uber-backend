package com.lekhana.uber.controller;

import com.lekhana.uber.model.Ride;
import com.lekhana.uber.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final RideService rideService;

    public UserController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping("/rides")
    public ResponseEntity<List<Ride>> getUserRides() {
        List<Ride> rides = rideService.getUserRides();
        return ResponseEntity.ok(rides);
    }
}
