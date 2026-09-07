package com.example.parkinglot.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.parkinglot.dto.RequestDto;
import com.example.parkinglot.dto.ResponseDto;
import com.example.parkinglot.entity.VechileParking;
import com.example.parkinglot.exception.vechileNumberExists;
import com.example.parkinglot.repository.ParkingVechileRepository;

import lombok.extern.slf4j.Slf4j;

@Service 
@Slf4j 
public class ParkVechileService {
    
    private ParkingVechileRepository parkingVechileRepository;

    public ParkVechileService(ParkingVechileRepository parkingVechileRepository){
        this.parkingVechileRepository=parkingVechileRepository;
    }

    public ResponseDto parkVechile(RequestDto request){
          log.info("Entered into the Parking vechile service {} " , request.getVechileNumber() );

          try{

            boolean isVechileNumberExists=parkingVechileRepository.vechileNumberExists(request.getVechileNumber() , LocalDate.now());
            if(isVechileNumberExists){

                log.info("Vechile number {} already exists in the parking lot" , request.getVechileNumber() );
                throw new vechileNumberExists("Vechile number already exists in the parking lot");
            }

            String Parking spot = 

            VechileParking parking = VechileParking.builder().parkingSpot()





             

          }
          catch(Exception e){
             log.info("Error occured while parking the vechile {} " , request.getVechileNumber() );
             throw new RuntimeException("Error occured while parking the vechile");
          }
    }


}
