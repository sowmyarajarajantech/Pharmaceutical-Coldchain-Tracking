package com.coldchain.repository;

import com.coldchain.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> { 
    // Spring Data JPA provides CRUD methods automatically
}
