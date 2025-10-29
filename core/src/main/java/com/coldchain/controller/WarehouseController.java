package com.coldchain.controller;

import com.coldchain.model.Warehouse;
import com.coldchain.repository.WarehouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend access
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    // GET all warehouses
    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // GET warehouse by ID
    @GetMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST add new warehouse
    @PostMapping("/warehouses")
    public Warehouse addWarehouse(@RequestBody Warehouse newWarehouse) {
        newWarehouse.setWarehouseId(null); // Ensure ID is null/0 for auto-generation
        return warehouseRepository.save(newWarehouse);
    }

    // PUT update warehouse
    @PutMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Integer id, @RequestBody Warehouse updatedWarehouse) {
        return warehouseRepository.findById(id)
            .map(existingWarehouse -> {
                updatedWarehouse.setWarehouseId(id); // Ensure ID is set correctly
                // You might want more specific field updates here instead of saving the whole object
                Warehouse saved = warehouseRepository.save(updatedWarehouse);
                return ResponseEntity.ok(saved);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE warehouse
    @DeleteMapping("/warehouses/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable Integer id) {
        if (warehouseRepository.existsById(id)) {
            warehouseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
