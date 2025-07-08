package com.aos.AOSBE.DTOS;

import java.time.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponsDTOS {
    private int id;
    private String code;
    private String description;
    private String discountType;
    private double discountValue;
    private double minOrderAmount;
    private double maxDiscountAmount;
    private int qty;
    private int usagePerCustomer;
    private boolean isAllowVoucher;
    private boolean isActive;
    private String customerGroup;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
