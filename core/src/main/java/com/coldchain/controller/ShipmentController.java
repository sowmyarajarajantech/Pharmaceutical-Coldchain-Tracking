package com.coldchain.controller;

import com.coldchain.model.Shipment;
import com.coldchain.repository.ShipmentRepository;

import org.springframework.http.ResponseEntity; // Needed for responses
import org.springframework.web.bind.annotation.*; // Adds POST, PUT, DELETE etc.

import java.util.List;
import java.util.Map;
import java.util.Optional; // Needed for finding by ID

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows your HTML file to talk to this
public class ShipmentController {

    private final ShipmentRepository shipmentRepository;

    public ShipmentController(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    // --- GET: Test Connection ---
    @GetMapping("/test")
    public Map<String, String> testConnection() {
        return Map.of("status", "Connection to server successful!");
    }

    // --- GET: Fetch All Shipments ---
    @GetMapping("/shipments")
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    // --- GET: Fetch Single Shipment by ID (Useful for editing) ---
    @GetMapping("/shipments/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Integer id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        if (shipment.isPresent()) {
            return ResponseEntity.ok(shipment.get());
        } else {
            return ResponseEntity.notFound().build(); // Send 404 if not found
        }
    }

    // --- POST: Add New Shipment ---
    // The @RequestBody tells Spring to take the JSON data sent from the HTML
    // form and turn it into a Shipment object.
    @PostMapping("/shipments")
    public Shipment addShipment(@RequestBody Shipment newShipment) {
        // We clear the ID so the database generates a new one
        newShipment.setShipmentId(0); // Assuming 0 or null signifies a new entry
        return shipmentRepository.save(newShipment); // Save to DB and return the saved object (with ID)
    }

    // --- PUT: Update Existing Shipment ---
    // We use @PathVariable to get the ID from the URL (e.g., /api/shipments/5)
    // We use @RequestBody for the updated data from the form.
    @PutMapping("/shipments/{id}")
    public ResponseEntity<Shipment> updateShipment(@PathVariable Integer id, @RequestBody Shipment updatedShipment) {
        // Check if shipment with this ID actually exists
        return shipmentRepository.findById(id)
            .map(existingShipment -> {
                // Update the fields (You might want more specific updates)
                // IMPORTANT: Make sure the ID from the URL is set on the updated object
                updatedShipment.setShipmentId(id); 
                Shipment savedShipment = shipmentRepository.save(updatedShipment);
                return ResponseEntity.ok(savedShipment); // Send back the updated shipment
            })
            .orElseGet(() -> ResponseEntity.notFound().build()); // Send 404 if not found
    }

    // --- DELETE: Delete Shipment by ID ---
    @DeleteMapping("/shipments/{id}")
    public ResponseEntity<?> deleteShipment(@PathVariable Integer id) {
        // Check if it exists before trying to delete
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
            return ResponseEntity.ok().build(); // Send success (200 OK)
        } else {
            return ResponseEntity.notFound().build(); // Send 404 if not found
        }
    }
}