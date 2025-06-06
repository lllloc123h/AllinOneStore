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
@Table(name = "shipping_methods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id\&quot;)
    private int id;
	
    @Column(name = "order_id\&quot;)
    private int orderId;
	
    @Column(name = "name\&quot;)
    private String name;
	
    @Column(name = "description\&quot;)
    private String description;
	
    @Column(name = "is_active\&quot;)
    private boolean isActive;
	@CreationTimestamp
    @Column(name = "created_at\&quot;,updatable &#61; false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at\&quot;)
    private LocalDateTime updatedAt;
   





}
