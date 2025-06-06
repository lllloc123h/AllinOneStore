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
	
    @Column(name = "id\&quot;)
    private int id;
	
    @Column(name = "reason\&quot;)
    private String reason;
	
    @Column(name = "image1\&quot;)
    private String image1;
	
    @Column(name = "image2\&quot;)
    private String image2;
	
    @Column(name = "image3\&quot;)
    private String image3;
	
    @Column(name = "is_returned_money\&quot;)
    private String isReturnedMoney;
	
    @Column(name = "is_returned_item\&quot;)
    private String isReturnedItem;
	
    @Column(name = "status\&quot;)
    private String status;
	@CreationTimestamp
    @Column(name = "created_at\&quot;,updatable &#61; false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at\&quot;)
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "order_product_item_id")
    private OrderItems orderItems;





}
