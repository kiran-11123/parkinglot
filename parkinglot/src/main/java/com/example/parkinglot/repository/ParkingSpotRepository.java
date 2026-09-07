package com.example.parkinglot.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parkinglot.entity.VechileParkingSpot;
public interface ParkingSpotRepository extends JpaRepository<VechileParkingSpot, Long> {



}
