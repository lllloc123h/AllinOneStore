package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDTOS {
	private int id;
	private String email;
	private String password;
	private boolean gender;
	private LocalDateTime birthday;
	private String fullname;
	private String avatarUrl;
	private String phone;
	private double averageOrderValue;
	private String userRank;
	private double totalSpent;
	private int totalOrder;
	private int loyaltyPoint;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public AccountsDTOS(int id, String email, boolean gender, LocalDateTime birthday, String fullname, String avatarUrl,
			String phone, double averageOrderValue, String userRank, double totalSpent, int totalOrder,
			int loyaltyPoint, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.email = email;
		this.gender = gender;
		this.birthday = birthday;
		this.fullname = fullname;
		this.avatarUrl = avatarUrl;
		this.phone = phone;
		this.averageOrderValue = averageOrderValue;
		this.userRank = userRank;
		this.totalSpent = totalSpent;
		this.totalOrder = totalOrder;
		this.loyaltyPoint = loyaltyPoint;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
