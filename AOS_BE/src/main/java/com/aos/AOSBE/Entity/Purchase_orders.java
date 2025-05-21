package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase_orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "supplier_id")
    private int supplier_id;
    @Column(name = "order_date")
    private LocalDateTime order_date;
    @Column(name = "expected_date")
    private LocalDateTime expected_date;
    @Column(name = "received_date")
    private LocalDateTime received_date;
    @Column(name = "total")
    private double total;
    @Column(name = "status")
    private String status;
    @Column(name = "note")
    private String note;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
