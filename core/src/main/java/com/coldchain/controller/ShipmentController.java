package com.coldchain.controller;

import com.coldchain.model.Shipment;
import com.coldchain.repository.ShipmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ShipmentController {

    private final ShipmentRepository shipmentRepository;

    public ShipmentController(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @GetMapping("/shipments")
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @GetMapping("/shipments/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Integer id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        return shipment.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/shipments")
    public Shipment addShipment(@RequestBody Shipment newShipment) {
        newShipment.setId(0); // Use the correct setter
        return shipmentRepository.save(newShipment);
    }

    @PutMapping("/shipments/{id}")
    public ResponseEntity<Shipment> updateShipment(@PathVariable Integer id, @RequestBody Shipment updatedShipment) {
        return shipmentRepository.findById(id)
            .map(existingShipment -> {
                updatedShipment.setId(id); // Use the correct setter
                Shipment savedShipment = shipmentRepository.save(updatedShipment);
                return ResponseEntity.ok(savedShipment);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/shipments/{id}")
    public ResponseEntity<?> deleteShipment(@PathVariable Integer id) {
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}