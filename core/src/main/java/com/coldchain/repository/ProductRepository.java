package com.coldchain.repository;

import com.coldchain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // JpaRepository gives us findAll(), findById(), save(), deleteById(), etc.
}