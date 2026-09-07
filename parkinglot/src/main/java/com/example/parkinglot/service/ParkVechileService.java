package com.example.parkinglot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.parkinglot.dto.RequestDto;
import com.example.parkinglot.dto.ResponseDto;
import com.example.parkinglot.dto.parkingSpotResponse;
import com.example.parkinglot.entity.VechileParking;
import com.example.parkinglot.entity.VechileParkingSpot;
import com.example.parkinglot.exception.vechileNumberExists;
import com.example.parkinglot.repository.ParkingVechileRepository;
import com.example.parkinglot.utils.ParkingSpotAllocator;

import lombok.extern.slf4j.Slf4j;

@Service 
@Slf4j 
public class ParkVechileService {
    
    private ParkingVechileRepository parkingVechileRepository;
    private  ParkingSpotAllocator parkingSpotAllocator;


    public ParkVechileService(ParkingVechileRepository parkingVechileRepository , ParkingSpotAllocator parkingSpotAllocator){
        this.parkingVechileRepository=parkingVechileRepository;
        this.parkingSpotAllocator=parkingSpotAllocator;
    }

    public ResponseDto parkVechile(RequestDto request){
          log.info("Entered into the Parking vechile service {} " , request.getVechileNumber() );

           LocalDate today = LocalDate.now();

LocalDateTime startOfDay = today.atStartOfDay();
LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
          

          try{

            boolean isVechileNumberExists=parkingVechileRepository.vechileNumberExists(request.getVechileNumber() , startOfDay , endOfDay);
            if(isVechileNumberExists){

                log.info("r {} already exists in the parking lot" , request.getVechileNumber() );
                throw new vechileNumberExists("Vechile number already exists in the parking lot");
            }

            
            parkingSpotResponse spotResponse = parkingSpotAllocator.parkVehicle(request.getVechileType());

            
            VechileParkingSpot vechileParkingSpot = VechileParkingSpot.builder()
                    .location(spotResponse.getLocation())
                    .spotId(spotResponse.getSpotId())
                    .parkingSpot(spotResponse.getParkingSpot())
                    .build();
            
            VechileParking vechileParking = VechileParking.builder()
                    .type(request.getVechileType())
                    .vechileNumber(request.getVechileNumber())
                    .entryTime(LocalDateTime.now())
                    .vechileParkingSpot(vechileParkingSpot)
                    .build();

            VechileParking savedVechileParking = parkingVechileRepository.save(vechileParking);

            return ResponseDto.builder()
                    .vechileNumber(savedVechileParking.getVechileNumber())
                    .entryTime(savedVechileParking.getEntryTime())
                    .parkingLocation(savedVechileParking.getVechileParkingSpot().getLocation())
                    .spotId(savedVechileParking.getVechileParkingSpot().getSpotId())
                    .parkingSpot(savedVechileParking.getVechileParkingSpot().getParkingSpot())
                    .build();

         





             

          }
          catch(Exception e){
             log.info("Error occured while parking the vechile {} " , request.getVechileNumber() );
             throw new RuntimeException("Error occured while parking the vechile");
          }
    }


}
