package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;
import java.math.*;
import java.util.*;
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
	
    @Column(name = "code")
    private String code;
	
    @Column(name = "description")
    private String description;
	
    @Column(name = "discount_type")
    private String discountType;
	
    @Column(name = "discount_value")
    private double discountValue;
	
    @Column(name = "min_order_amount")
    private double minOrderAmount;
	
    @Column(name = "max_discount_amount")
    private double maxDiscountAmount;
	
    @Column(name = "usage_limit")
    private int usageLimit;
	
    @Column(name = "usage_per_customer")
    private int usagePerCustomer;
	
    @Column(name = "is_allow_voucher")
    private boolean isAllowVoucher;
	
    @Column(name = "is_active")
    private boolean isActive;
	
    @Column(name = "customer_group")
    private String customerGroup;
	
    @Column(name = "start_at")
    private LocalDateTime startAt;
	
    @Column(name = "end_at")
    private LocalDateTime endAt;
	@CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   





}
