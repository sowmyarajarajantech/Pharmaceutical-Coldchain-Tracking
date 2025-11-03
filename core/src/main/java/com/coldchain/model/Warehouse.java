package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wh_id; // Matches 'wh_id'

    private String wh_code;
    private String wh_name;
    private String address;
    private String phone;
    private LocalDateTime created_at;

    // --- Getters and Setters ---
    public int getWh_id() { return wh_id; }
    public void setWh_id(int wh_id) { this.wh_id = wh_id; }
    public String getWh_code() { return wh_code; }
    public void setWh_code(String wh_code) { this.wh_code = wh_code; }
    public String getWh_name() { return wh_name; }
    public void setWh_name(String wh_name) { this.wh_name = wh_name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}