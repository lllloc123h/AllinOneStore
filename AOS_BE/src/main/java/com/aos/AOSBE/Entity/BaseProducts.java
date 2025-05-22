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
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "main_image")
    private String mainImage;
    @Column(name = "turn_buy")
    private int turnBuy;
    @Column(name = "rating")
    private int rating;
    @Column(name = "for_sale")
    private boolean forSale;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   





}
