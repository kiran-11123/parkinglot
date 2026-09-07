package com.example.parkinglot.utils;

import com.example.parkinglot.dto.parkingSpotResponse;
import com.example.parkinglot.entity.ParkingLocation;
import com.example.parkinglot.entity.VechileType;
import com.example.parkinglot.exception.ParkingSpotNotAvailableException;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class ParkingSpotAllocator {

    private final Map<
            ParkingLocation,
            Map<VechileType, PriorityQueue<Integer>>
            > parkSpot = new HashMap<>();


    public parkingSpotResponse parkVehicle(VechileType type) {

        for (ParkingLocation location : parkSpot.keySet()) {

            Map<VechileType, PriorityQueue<Integer>> spotsByType =
                    parkSpot.get(location);

            PriorityQueue<Integer> availableSpots =
                    spotsByType.get(type);

            if (availableSpots != null && !availableSpots.isEmpty()) {

                int spotId = availableSpots.poll();

                return parkingSpotResponse.builder()
                        .location(location)
                        .spotId(spotId)
                        .parkingSpot(type== VechileType.BIKE ? com.example.parkinglot.entity.ParkingSpot.BIKE_SPOT : com.example.parkinglot.entity.ParkingSpot.CAR_SPOT)
                        .build();
            }
        }

        throw new ParkingSpotNotAvailableException(
                "No parking spot available for vehicle type: " + type
        );
    }
}