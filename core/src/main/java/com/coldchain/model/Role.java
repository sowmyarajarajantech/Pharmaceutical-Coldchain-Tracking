package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id; // Matches schema

    private String role_name;
    private String description;

    // --- Getters and Setters ---
    public int getRole_id() { return role_id; }
    public void setRole_id(int role_id) { this.role_id = role_id; }
    public String getRole_name() { return role_name; }
    public void setRole_name(String role_name) { this.role_name = role_name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}