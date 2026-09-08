package com.example.parkinglot.controller;

import com.example.parkinglot.dto.ApiResponse;
import com.example.parkinglot.dto.GetParkingSlotsResponse;
import com.example.parkinglot.dto.RequestDto;
import com.example.parkinglot.dto.ResponseDto;
import com.example.parkinglot.entity.VechileType;
import com.example.parkinglot.service.ParkVechileService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j 
@RequestMapping("/api/park")
public class ParkVechileController {

    private final ParkVechileService parkVechileService;

    public ParkVechileController(ParkVechileService parkVechileService) {
        this.parkVechileService = parkVechileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResponseDto>> parkVechile(@Valid @RequestBody RequestDto request) {
        ResponseDto response = parkVechileService.parkVechile(request);
        return ResponseEntity.ok(ApiResponse.<ResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message("Vechile Parking Booked successfully")
                .data(response)
                .build());
    }

    @GetMapping("{vechileNumber}")
    public ResponseEntity<ApiResponse<ResponseDto>> getParkingDetails( @PathVariable String vechileNumber) {
        ResponseDto response = parkVechileService.getVechileDetails(vechileNumber);
        return ResponseEntity.ok(ApiResponse.<ResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message("Vechile Parking Details fetched successfully")
                .data(response)
                .build());
    }



    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Parking Lot Service is up and running")
                .data("Service is healthy")
                .build());
    }

    @PostMapping("/release/{vechileNumber}")
    public ResponseEntity<ApiResponse<String>> releaseParkingSpot(@PathVariable String vechileNumber){
           log.info("Entered into the release parking spot controller {} " , vechileNumber );
           parkVechileService.releaseParkingSpot(vechileNumber);

              return ResponseEntity.ok(ApiResponse.<String>builder()
                 .status(HttpStatus.OK.value())
                 .message("Vechile Parking Spot released successfully")
                 .data("Parking Spot released for vechile number: " + vechileNumber)
                 .build());

    }

    
    @GetMapping("/getSlots/{vechileType}")
    public ResponseEntity<ApiResponse<GetParkingSlotsResponse>> getParkingSlotCount(@PathVariable VechileType vechileType){
           
        log.info("Entered into getParkingSlot Count controller for VechileType , {}" , vechileType);
        GetParkingSlotsResponse response  = parkVechileService.getParkingSlots(vechileType);
        ApiResponse<GetParkingSlotsResponse> result  = ApiResponse.<GetParkingSlotsResponse>builder().status(200).message("Available slot details fetched successfully").data(response).build();
        return  ResponseEntity.ok(result);



    }



}
