package com.lekhana.uber.service;

import com.lekhana.uber.dto.CreateRideRequest;
import com.lekhana.uber.exception.NotFoundException;
import com.lekhana.uber.model.Ride;
import com.lekhana.uber.repository.RideRepository;
import com.lekhana.uber.exception.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RideService {
    private final RideRepository rideRepository;
    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
    public Ride createRide(CreateRideRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Ride ride = new Ride();
        ride.setUserId(username);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        return rideRepository.save(ride);
    }
    public List<Ride> getPendingRides() {
        return rideRepository.findByStatus("REQUESTED");
    }
    public Ride acceptRide(String rideId) {
        String driverUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        if (!ride.getStatus().equals("REQUESTED")) {
            throw new BadRequestException("Ride is not available");
        }
        ride.setDriverId(driverUsername);
        ride.setStatus("ACCEPTED");
        return rideRepository.save(ride);
    }
    public Ride completeRide(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new BadRequestException("Ride must be accepted first");
        }
        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }
    public List<Ride> getUserRides() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rideRepository.findByUserId(username);
    }
}
