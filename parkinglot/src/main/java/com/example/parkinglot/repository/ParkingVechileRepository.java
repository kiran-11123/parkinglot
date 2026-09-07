package com.example.parkinglot.repository;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parkinglot.entity.VechileParking;


public interface ParkingVechileRepository extends JpaRepository<VechileParking, Long> {
    
    Optional<VechileParking> findByVechileNumber(String vechileNumber);

    boolean vechileNumberExists(String vechileNumber , LocalDate date);
}
