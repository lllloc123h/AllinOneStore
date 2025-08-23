package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTOS {
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
	private String orderCode;
    private List<OrderItemDTO> items;
    private List<CustomsDTOS> customs;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDTO {
        private int id;
        @JsonProperty("quantity")
        private int qty;
        private double priceAtBuy;
        private double costAtBuy;
        @JsonProperty("isGift")
        private Boolean isGift;
        private double sellingPrice;
        private double total;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private int orders;
        private int productItems;
        private int promotions;
        private String promotionName;
        private String comboGroup;
        private UUID comboGroupId;
        private Integer comboQty;
        private Integer productItemId;
        private String name;
        private String main_image_url;
        private double price;
        private ProductDTO product;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDTO {
        private int productId;
        private String sku;
        private String productName;
        private String imageUrl;
    }
}
