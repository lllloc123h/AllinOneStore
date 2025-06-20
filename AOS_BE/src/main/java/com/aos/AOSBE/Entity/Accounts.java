package com.aos.AOSBE.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "avatar_url")
	private String avatar;

	@Column(name = "phone")
	private String phone;

	@Column(name = "average_order_value", insertable = false)
	private double averageOrderValue;

	@Column(name = "user_rank", insertable = false)
	private String userRank;

	@Column(name = "total_spent", insertable = false)
	private double totalSpent;

	@Column(name = "total_order", insertable = false)
	private int totalOrder;

	@Column(name = "loyalty_point", insertable = false)
	private int loyaltyPoint;
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
