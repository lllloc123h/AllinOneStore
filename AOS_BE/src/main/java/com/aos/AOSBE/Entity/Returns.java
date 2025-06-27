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
@Table(name = "returns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Returns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "qty")
    private int qty;
	
    @Column(name = "reason")
    private String reason;
	
    @Column(name = "image_url1")
    private String imageUrl1;
	
    @Column(name = "image_url2")
    private String imageUrl2;
	
    @Column(name = "image_url3")
    private String imageUrl3;
	
    @Column(name = "video_url")
    private String videoUrl;
	
    @Column(name = "is_returned_money")
    private boolean isReturnedMoney;
	
    @Column(name = "refund_amount")
    private double refundAmount;
	
    @Column(name = "return_type")
    private String returnType;
	
    @Column(name = "refund_type")
    private String refundType;
	
    @Column(name = "status")
    private String status;
	
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "order_product_item_id")
    private OrderItems orderItems;





}
