package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
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
    private int promotion_id;
    @Column(name = "item_id")
    private int item_id;
    @Column(name = "discount_type")
    private String discount_type;
    @Column(name = "discount_value")
    private double discount_value;
    @Column(name = "max_discount")
    private double max_discount;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
