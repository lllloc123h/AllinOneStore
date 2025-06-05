package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cancels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cancels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 
    @Column(name = "id")
    private int id;
	 
    @Column(name = "reason")
    private String reason;
	 
    @Column(name = "is_paid")
    private boolean isPaid;
	 
    @Column(name = "status")
    private String status;
	 @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
	 @@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;





}
