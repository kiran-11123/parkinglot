package com.example.parkinglot.repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.parkinglot.entity.VechileParking;


public interface ParkingVechileRepository extends JpaRepository<VechileParking, Long> {
    
    Optional<VechileParking> findByVechileNumber(String vechileNumber);
    
    @Query("""
    SELECT COUNT(v) > 0
    FROM VechileParking v
    WHERE v.vechileNumber = :vechileNumber
    AND v.entryTime >= :startOfDay
    AND v.entryTime < :endOfDay
""")
boolean vechileNumberExists(
        @Param("vechileNumber") String vechileNumber,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
);


}
