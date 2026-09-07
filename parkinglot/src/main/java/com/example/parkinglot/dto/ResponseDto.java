package com.example.parkinglot.dto;

import com.example.parkinglot.entity.ParkingLocation;
import com.example.parkinglot.entity.ParkingSpot;
import com.example.parkinglot.entity.VechileType;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data 
@Builder 
public class ResponseDto {
     
    private String vechileNumber;
    private VechileType vechileType;
    private  ParkingLocation parkingLocation;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private int spotId;
}
