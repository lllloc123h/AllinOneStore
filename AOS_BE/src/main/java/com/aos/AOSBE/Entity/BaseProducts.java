package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "base_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "material")
    private String material;
    @Column(name = "main_image")
    private String mainImage;
    @Column(name = "is_custom")
    private boolean isCustom;
    @Column(name = "turn_buy")
    private int turnBuy;
    @Column(name = "rating")
    private int rating;
    @Column(name = "is_discount")
    private boolean isDiscount;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories categories;





}
