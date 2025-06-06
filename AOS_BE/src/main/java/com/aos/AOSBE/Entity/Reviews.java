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
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id\&quot;)
    private int id;
	
    @Column(name = "rating\&quot;)
    private int rating;
	
    @Column(name = "comment\&quot;)
    private String comment;
	
    @Column(name = "images\&quot;)
    private String images;
	@CreationTimestamp
    @Column(name = "created_at\&quot;,updatable &#61; false)
    private LocalDateTime createdAt;
   
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;
    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItems productItems;





}
