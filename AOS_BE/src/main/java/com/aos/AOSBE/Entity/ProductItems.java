package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
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
    @Column(name = "base_id")
    private int base_id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private double cost;
    @Column(name = "price")
    private double price;
    @Column(name = "unit")
    private String unit;
    @Column(name = "turn_buy")
    private int turn_buy;
    @Column(name = "description")
    private String description;
    @Column(name = "for_sale")
    private boolean for_sale;
    @Column(name = "sku")
    private String sku;
    @Column(name = "safety_stock")
    private int safety_stock;
    @Column(name = "qty")
    private int qty;
    @Column(name = "sell_start")
    private LocalDateTime sell_start;
    @Column(name = "sell_end")
    private LocalDateTime sell_end;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
