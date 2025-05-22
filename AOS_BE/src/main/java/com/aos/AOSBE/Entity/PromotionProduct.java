package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PromotionProduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "promotion_id")
    private int promotionId;
    @Column(name = "item_id")
    private int itemId;
    @Column(name = "discount_type")
    private String discountType;
    @Column(name = "discount_value")
    private double discountValue;
    @Column(name = "max_discount")
    private double maxDiscount;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id")
    private BaseProducts baseBaseProducts;





}
