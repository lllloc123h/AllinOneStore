package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "expected_date")
    private LocalDate expectedDate;
    @Column(name = "received_date")
    private LocalDate receivedDate;
    @Column(name = "total")
    private double total;
    @Column(name = "status")
    private String status;
    @Column(name = "note")
    private String note;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
   





}
