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
@Table(name = "PromotionProduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "require_qty")
    private int requireQty;
	
    @Column(name = "is_gift")
    private boolean isGift;
	
    @Column(name = "cost_share")
    private double costShare;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItems productItems;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotions promotions;





}
