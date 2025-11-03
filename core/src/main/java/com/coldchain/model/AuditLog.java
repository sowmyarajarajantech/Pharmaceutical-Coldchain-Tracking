package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "AuditLog")
public class AuditLog {

    @Id
    private long id; // Matches BIGINT

    private Integer user_id; // Use Integer for nullable INT
    private String action;
    private String details;
    private LocalDateTime created_at;

    // --- Getters and Setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}