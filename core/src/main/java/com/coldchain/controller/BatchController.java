package com.coldchain.controller;

import com.coldchain.model.Batch;
import com.coldchain.repository.BatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BatchController {

    private final BatchRepository batchRepository;

    public BatchController(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @GetMapping("/batches")
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    @GetMapping("/batches/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable Integer id) {
        Optional<Batch> batch = batchRepository.findById(id);
        return batch.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/batches")
    public Batch addBatch(@RequestBody Batch newBatch) {
        newBatch.setId(0);
        return batchRepository.save(newBatch);
    }

    @PutMapping("/batches/{id}")
    public ResponseEntity<Batch> updateBatch(@PathVariable Integer id, @RequestBody Batch updatedBatch) {
        return batchRepository.findById(id)
            .map(existingBatch -> {
                updatedBatch.setId(id);
                Batch savedBatch = batchRepository.save(updatedBatch);
                return ResponseEntity.ok(savedBatch);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/batches/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Integer id) {
        if (batchRepository.existsById(id)) {
            batchRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}