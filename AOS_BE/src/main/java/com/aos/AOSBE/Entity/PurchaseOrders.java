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
    @Column(name = "supplier_id")
    private int supplierId;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "expected_date")
    private LocalDateTime expectedDate;
    @Column(name = "received_date")
    private LocalDateTime receivedDate;
    @Column(name = "total")
    private double total;
    @Column(name = "status")
    private String status;
    @Column(name = "note")
    private String note;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ProductItems productProductItems;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrders purchasePurchaseOrders;





}
