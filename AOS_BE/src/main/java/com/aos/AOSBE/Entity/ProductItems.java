package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private double cost;
    @Column(name = "price")
    private double price;
    @Column(name = "is_promote")
    private boolean isPromote;
    @Column(name = "turn_buy")
    private int turnBuy;
    @Column(name = "description")
    private String description;
    @Column(name = "sku")
    private String sku;
    @Column(name = "safety_stock")
    private int safetyStock;
    @Column(name = "qty")
    private int qty;
    @Column(name = "sell_start")
    private LocalDate sellStart;
    @Column(name = "sell_end")
    private LocalDate sellEnd;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id")
    private BaseProducts baseProducts;





}
