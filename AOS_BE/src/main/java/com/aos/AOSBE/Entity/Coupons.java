package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "order_item")
    private int order_item;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "discount_type")
    private String discount_type;
    @Column(name = "discount_value")
    private double discount_value;
    @Column(name = "min_order_amount")
    private double min_order_amount;
    @Column(name = "max_discount_amount")
    private double max_discount_amount;
    @Column(name = "usage_limit")
    private int usage_limit;
    @Column(name = "usage_per_customer")
    private int usage_per_customer;
    @Column(name = "start_date")
    private LocalDateTime start_date;
    @Column(name = "end_date")
    private LocalDateTime end_date;
    @Column(name = "active")
    private boolean active;
    @Column(name = "customer_group")
    private String customer_group;
    @Column(name = "start_at")
    private LocalDateTime start_at;
    @Column(name = "end_at")
    private LocalDateTime end_at;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
