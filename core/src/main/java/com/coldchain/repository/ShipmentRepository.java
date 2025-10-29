package com.coldchain.repository; // <-- This now matches its folder

import com.coldchain.model.Shipment; // It will import your Shipment model
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    // That's it!
    // Spring Data JPA automatically creates all the database
    // methods (.findAll(), .save(), etc.) from this one line.
}