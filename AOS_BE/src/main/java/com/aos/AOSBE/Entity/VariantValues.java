package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "variant_values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 
    @Column(name = "id")
    private int id;
	 
    @Column(name = "signal_sku")
    private String signalSku;
	 
    @Column(name = "description")
    private String description;
	 @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
	 @@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variants variants;





}
