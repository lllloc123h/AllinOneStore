package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "purchase_order_id")
    private int purchaseOrderId;
    @Column(name = "item_id")
    private int itemId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "cost")
    private double cost;
    @Column(name = "total_cost")
    private double totalCost;
   





}
