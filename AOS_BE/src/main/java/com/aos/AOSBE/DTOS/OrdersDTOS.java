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
	private Double discountValue;
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
}
