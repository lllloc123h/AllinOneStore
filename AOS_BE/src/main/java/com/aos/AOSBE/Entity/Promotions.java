package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promotions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 
    @Column(name = "id")
    private int id;
	 
    @Column(name = "name")
    private String name;
	 
    @Column(name = "description")
    private String description;
	 
    @Column(name = "type")
    private String type;
	 
    @Column(name = "discount_type")
    private String discountType;
	 
    @Column(name = "discount_value")
    private double discountValue;
	 
    @Column(name = "combo_price")
    private double comboPrice;
	 
    @Column(name = "usage_limit")
    private int usageLimit;
	 
    @Column(name = "start_at")
    private LocalDateTime startAt;
	 
    @Column(name = "end_at")
    private LocalDateTime endAt;
	 
    @Column(name = "is_active")
    private boolean isActive;
	 @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
	 @@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   





}
