// File: core/src/main/java/com/coldchain/model/User.java
package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id; // Matches schema

    private String username;
    private String full_name;
    private String email;
    private String password_hash; // We will just treat this as text for the simple dashboard
    private int role_id; // Matches schema

    // --- Getters and Setters ---
    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFull_name() { return full_name; }
    public void setFull_name(String full_name) { this.full_name = full_name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword_hash() { return password_hash; }
    public void setPassword_hash(String password_hash) { this.password_hash = password_hash; }
    public int getRole_id() { return role_id; }
    public void setRole_id(int role_id) { this.role_id = role_id; }
}