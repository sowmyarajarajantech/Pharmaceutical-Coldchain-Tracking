package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Batches")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Matches schema

    private int product_id;
    private String batch_no;
    private LocalDate manufacture_date; // Use LocalDate for DATE type
    private LocalDate expiry_date;
    private int quantity;
    private boolean is_expired;

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProduct_id() { return product_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id; }
    public String getBatch_no() { return batch_no; }
    public void setBatch_no(String batch_no) { this.batch_no = batch_no; }
    public LocalDate getManufacture_date() { return manufacture_date; }
    public void setManufacture_date(LocalDate mdate) { this.manufacture_date = mdate; }
    public LocalDate getExpiry_date() { return expiry_date; }
    public void setExpiry_date(LocalDate edate) { this.expiry_date = edate; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public boolean getIs_expired() { return is_expired; }
    public void setIs_expired(boolean is_expired) { this.is_expired = is_expired; }
}