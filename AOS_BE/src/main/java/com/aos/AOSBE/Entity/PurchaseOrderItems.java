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
@Table(name = "purchase_order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "qty")
    private int qty;
	
    @Column(name = "cost")
    private double cost;
	
    @Column(name = "total_cost")
    private double totalCost;
	@CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItems productItems;
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrders purchaseOrders;





}
