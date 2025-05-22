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
    @Column(name = "base_id")
    private int baseId;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private double cost;
    @Column(name = "price")
    private double price;
    @Column(name = "unit")
    private String unit;
    @Column(name = "turn_buy")
    private int turnBuy;
    @Column(name = "description")
    private String description;
    @Column(name = "for_sale")
    private boolean forSale;
    @Column(name = "sku")
    private String sku;
    @Column(name = "safety_stock")
    private int safetyStock;
    @Column(name = "qty")
    private int qty;
    @Column(name = "sell_start")
    private LocalDateTime sellStart;
    @Column(name = "sell_end")
    private LocalDateTime sellEnd;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ProductItems productProductItems;





}
