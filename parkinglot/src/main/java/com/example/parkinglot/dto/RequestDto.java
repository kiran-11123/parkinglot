package com.example.parkinglot.dto;

import com.example.parkinglot.entity.VechileType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class RequestDto {
     
    @NotBlank (message = "Vechile Number is required")
    private String vechileNumber; 
   
    @NotBlank (message = "Vechile Type is required" )
    private VechileType vechileType;
    
}
