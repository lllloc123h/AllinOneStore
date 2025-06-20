package com.aos.AOSBE.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "base_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "material")
	private String material;

	@Column(name = "main_image_url")
	private String mainImage;

	@Column(name = "is_custom")
	private boolean isCustom;

	@Column(name = "turn_buy")
	private int turnBuy;

	@Column(name = "rating")
	private int rating;

	@Column(name = "is_active")
	private boolean isActive;
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories categories;

}
