package com.example.parkinglot.utils;

import com.example.parkinglot.dto.parkingSpotResponse;
import com.example.parkinglot.entity.ParkingLocation;
import com.example.parkinglot.entity.VechileType;
import com.example.parkinglot.exception.ParkingSpotNotAvailableException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Slf4j 
public class ParkingSpotAllocator {

    private final Map<
            ParkingLocation,
            Map<VechileType, PriorityQueue<Integer>>
            > parkSpot = new HashMap<>();
   
            public ParkingSpotAllocator() {
               initializeParkingSpots();
            }

    public parkingSpotResponse parkVehicle(VechileType type) {
        log.info("Attempting to park vehicle of type: {}", type);
        for (ParkingLocation location : parkSpot.keySet()) {

            Map<VechileType, PriorityQueue<Integer>> spotsByType =
                    parkSpot.get(location);

            PriorityQueue<Integer> availableSpots =
                    spotsByType.get(type);

            if (availableSpots != null && !availableSpots.isEmpty()) {

                int spotId = availableSpots.poll();

                log.info("Vehicle parked successfully at location: {}, spot ID: {}", location, spotId);
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

    public void initializeParkingSpots() {
        log.info("Initializing parking spots for all locations and vehicle types");
        for (ParkingLocation location : ParkingLocation.values()) {
            Map<VechileType, PriorityQueue<Integer>> spotsByType = new HashMap<>();

            for (VechileType type : VechileType.values()) {
                PriorityQueue<Integer> availableSpots = new PriorityQueue<>();
                int totalSpots = (type == VechileType.BIKE) ? 10 : 20;

                for (int i = 1; i <= totalSpots; i++) {
                    availableSpots.offer(i);
                }

                spotsByType.put(type, availableSpots);
            }

            parkSpot.put(location, spotsByType);
        }
    }


    public boolean releaseParkingSpot(ParkingLocation location , VechileType type , int spotId){
        log.info("Releasing parking spot at location: {}, type: {}, spot ID: {}", location, type, spotId);
        Map<VechileType,PriorityQueue<Integer>> spotByType = parkSpot.get(location);

        if(spotByType != null){
            PriorityQueue<Integer> availableSpots = spotByType.get(type);
            if(availableSpots != null){
                availableSpots.offer(spotId);
                log.info("Parking spot released at location: {}, spot ID: {}", location, spotId);
                return true;
            }
        }
        return false;

    }

    public int getParkingSlots(VechileType vechileType){
        log.info("Fetching available parking slots for vehicle type: {}", vechileType);

          int count = 0;
            
          for(ParkingLocation  location : parkSpot.keySet()){
                 
                Map<VechileType , PriorityQueue<Integer>> map = parkSpot.get(location);

                count+=map.get(vechileType).size();
          }

          return count;
    }
}