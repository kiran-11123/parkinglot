package com.example.parkinglot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table (name = "vechile_parking")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class VechileParking {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated (EnumType.STRING)
    private VechileType type;

    @Column (name = "vechile_number", nullable = false, unique = true)
    private String vechileNumber;
 
     
    @Column (name = "entry_time", nullable = false)
    private LocalDateTime entryTime;
    


    @Column (name = "exit_time")
    private LocalDateTime exitTime;

    @OneToOne(mappedBy = "vehicleParking", cascade = CascadeType.ALL, orphanRemoval = true)
    private VechileParkingSpot vechileParkingSpot;

}
