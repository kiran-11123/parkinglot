package com.example.parkinglot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table (name = "vechile_parking_spot")
@Data 
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VechileParkingSpot {
     
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    @Enumerated (EnumType.STRING)
    @Column (name = "parking_location", nullable = false)
    private ParkingLocation location;


    @Enumerated (EnumType.STRING)
    @Column (name = "parking_spot", nullable = false)
    private ParkingSpot parkingSpot;
    
    @Column (name = "spot_id", nullable = false)
    private int spotId;

       @OneToOne
    @JoinColumn(
        name = "vehicle_parking_id",
        nullable = false,
        unique = true
    )
    private VechileParking vehicleParking;
}
