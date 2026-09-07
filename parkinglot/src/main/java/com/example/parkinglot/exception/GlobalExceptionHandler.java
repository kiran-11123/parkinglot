package com.example.parkinglot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.parkinglot.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice 
@Slf4j 
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception e) {

        log.error(
                "Unexpected error occurred",
                e
        );

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Something went wrong")
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

      @ExceptionHandler(vechileNumberExists.class)
    public ResponseEntity<ApiResponse<Void>> vechileNumberExists(
            vechileNumberExists e) {

        log.error(
                "Vechile number already exists",
                e
        );

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message("Vechile number already exists in the parking lot")
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


      @ExceptionHandler(ParkingSpotNotAvailableException.class)
    public ResponseEntity<ApiResponse<Void>> ParkingSpotNotAvailable(
            ParkingSpotNotAvailableException e) {

        log.error(
                "Parking spot not available",
                e
        );

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("No parking spot available for the requested vehicle type")
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    
      @ExceptionHandler(VechileNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> vechileNotFound(
            VechileNotFoundException e) {

        log.error(
                "Vechile not found",
                e
        );

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Vechile not found in the parking lot")
                        .data(null)
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


}
