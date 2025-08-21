package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private Integer id;
	@ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts account;
	
    @Column(name = "canvas_json")
    private String canvasJson;
	
    @Column(name = "image_url")
    private String imageUrl;
	
    @Column(name = "design_name")
    private String designName;
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
    @JoinColumn(name = "order_id")
    private Orders order;
}
