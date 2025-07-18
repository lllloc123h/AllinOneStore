package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTOS {
	private int id;
	private String shippingStatus;
	private double estimatedShippingFee;
	private String freeshipCouponCode;
	private double actualShippingFee;
	private String discountCouponCode;
	private double discountValue;
	private LocalDateTime shippedDate;
	private String paymentStatus;
	private String note;
	private int point;
	private double finalTotal;
	private String orderInfor;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int accounts;
	private int paymentMethods;
	private int shippingMethods;

	private List<?> productOrder;

	public OrdersDTOS(int id, String shippingStatus, double estimatedShippingFee, String freeshipCouponCode,
			double actualShippingFee, String discountCouponCode, double discountValue, LocalDateTime shippedDate,
			String paymentStatus, String note, int point, double finalTotal, String orderInfor, LocalDateTime createdAt,
			LocalDateTime updatedAt, int accounts, int paymentMethods, int shippingMethods) {
		super();
		this.id = id;
		this.shippingStatus = shippingStatus;
		this.estimatedShippingFee = estimatedShippingFee;
		this.freeshipCouponCode = freeshipCouponCode;
		this.actualShippingFee = actualShippingFee;
		this.discountCouponCode = discountCouponCode;
		this.discountValue = discountValue;
		this.shippedDate = shippedDate;
		this.paymentStatus = paymentStatus;
		this.note = note;
		this.point = point;
		this.finalTotal = finalTotal;
		this.orderInfor = orderInfor;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accounts = accounts;
		this.paymentMethods = paymentMethods;
		this.shippingMethods = shippingMethods;
	}

}
