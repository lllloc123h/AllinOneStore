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
@Table(name = "variant_values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id\&quot;)
    private int id;
	
    @Column(name = "signal_sku\&quot;)
    private String signalSku;
	
    @Column(name = "description\&quot;)
    private String description;
	@CreationTimestamp
    @Column(name = "created_at\&quot;,updatable &#61; false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at\&quot;)
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variants variants;





}
