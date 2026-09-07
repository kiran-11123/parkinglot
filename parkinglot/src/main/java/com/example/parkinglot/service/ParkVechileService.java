package com.example.parkinglot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.parkinglot.dto.RequestDto;
import com.example.parkinglot.dto.ResponseDto;
import com.example.parkinglot.dto.parkingSpotResponse;
import com.example.parkinglot.entity.VechileParking;
import com.example.parkinglot.entity.VechileParkingSpot;
import com.example.parkinglot.exception.ParkingSpotNotAvailableException;
import com.example.parkinglot.exception.VechileNotFoundException;
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

                vechileParkingSpot.setVehicleParking(vechileParking);

            VechileParking savedVechileParking = parkingVechileRepository.save(vechileParking);

            return ResponseDto.builder()
                    .vechileNumber(savedVechileParking.getVechileNumber())
                    .entryTime(savedVechileParking.getEntryTime())
                    .parkingLocation(savedVechileParking.getVechileParkingSpot().getLocation())
                    .spotId(savedVechileParking.getVechileParkingSpot().getSpotId())
                    .parkingSpot(savedVechileParking.getVechileParkingSpot().getParkingSpot())
                    .vechileType(savedVechileParking.getType())
                    .build();

         





             

          }
           catch (vechileNumberExists | ParkingSpotNotAvailableException e) {
               throw e;
           }
           catch(Exception e){
                 log.error("Error occurred while parking the vehicle {}", request.getVechileNumber(), e);
                 throw new RuntimeException("Error occurred while parking the vehicle", e);
          }
    }

    public  ResponseDto getVechileDetails(String vechileNumber){
        log.info("Fetching vehicle details for vehicle number: {}", vechileNumber);

        return parkingVechileRepository.findByVechileNumber(vechileNumber)
                .map(vechileParking -> ResponseDto.builder()
                        .vechileNumber(vechileParking.getVechileNumber())
                        .entryTime(vechileParking.getEntryTime())
                        .parkingLocation(vechileParking.getVechileParkingSpot().getLocation())
                        .spotId(vechileParking.getVechileParkingSpot().getSpotId())
                        .parkingSpot(vechileParking.getVechileParkingSpot().getParkingSpot())
                        .vechileType(vechileParking.getType())
                        .build())
                .orElseThrow(() -> {
                    log.error("Vehicle number {} not found in the parking lot", vechileNumber);
                    return new VechileNotFoundException("Vehicle number not found in the parking lot");
                });
    }


}
