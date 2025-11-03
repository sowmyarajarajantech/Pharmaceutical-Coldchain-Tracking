package com.coldchain.controller;

import com.coldchain.model.Warehouse;
import com.coldchain.repository.WarehouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @GetMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        return warehouse.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/warehouses")
    public Warehouse addWarehouse(@RequestBody Warehouse newWarehouse) {
        newWarehouse.setWh_id(0); 
        return warehouseRepository.save(newWarehouse);
    }

    @PutMapping("/warehouses/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Integer id, @RequestBody Warehouse updatedWarehouse) {
        return warehouseRepository.findById(id)
            .map(existingWarehouse -> {
                updatedWarehouse.setWh_id(id); // Use the correct setter
                Warehouse savedWarehouse = warehouseRepository.save(updatedWarehouse);
                return ResponseEntity.ok(savedWarehouse);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

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