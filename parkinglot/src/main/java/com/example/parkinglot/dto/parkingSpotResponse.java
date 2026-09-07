package com.example.parkinglot.dto;

import com.example.parkinglot.entity.ParkingLocation;
import com.example.parkinglot.entity.ParkingSpot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class parkingSpotResponse {
     
    private ParkingLocation location;
    private ParkingSpot parkingSpot;
    private  int spotId;

}
