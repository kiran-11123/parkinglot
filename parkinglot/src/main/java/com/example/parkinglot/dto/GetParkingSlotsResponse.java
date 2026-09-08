package com.example.parkinglot.dto;

import com.example.parkinglot.entity.VechileType;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class GetParkingSlotsResponse {
     
    private VechileType type;
    private int slotsAvailable;
    
}
