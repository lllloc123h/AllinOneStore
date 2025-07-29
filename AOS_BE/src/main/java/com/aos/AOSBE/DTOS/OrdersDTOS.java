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

	private int paymentMethodId;
	private String paymentMethodName;

	private int shippingMethodId;
	private String shippingMethodName;

	private List<OrderItemsDTOS> products;
	private String orderCode;

	public OrdersDTOS(
		int id, String shippingStatus, double estimatedShippingFee, String freeshipCouponCode,
		double actualShippingFee, String discountCouponCode, double discountValue, LocalDateTime shippedDate,
		String paymentStatus, String note, int point, double finalTotal, String orderInfor, LocalDateTime createdAt,
		LocalDateTime updatedAt, int accounts,
		int paymentMethodId, String paymentMethodName,
		int shippingMethodId, String shippingMethodName,
		String orderCode,List<OrderItemsDTOS> products
	) {
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
		this.paymentMethodId = paymentMethodId;
		this.paymentMethodName = paymentMethodName;
		this.shippingMethodId = shippingMethodId;
		this.shippingMethodName = shippingMethodName;
		this.orderCode = orderCode;
		this.products = products;
	}
}
