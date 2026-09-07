package com.example.parkinglot.exception;

public class VechileNotFoundException extends RuntimeException {
    
    public VechileNotFoundException(String message){
        super(message);
    }

}
