package com.coldchain.controller;

import com.coldchain.model.Product;
import com.coldchain.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET /api/products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET /api/products/1
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/products
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product newProduct) {
        newProduct.setId(0); // Ensure it's a new product
        return productRepository.save(newProduct);
    }

    // PUT /api/products/1
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
            .map(existingProduct -> {
                updatedProduct.setId(id); // Set the ID to ensure update
                Product savedProduct = productRepository.save(updatedProduct);
                return ResponseEntity.ok(savedProduct);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/products/1
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}