package com.aos.AOSBE.DTOS;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
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
    private int usageLimit;
    private int usagePerCustomer;
    private boolean isAllowVoucher;
    private boolean isActive;
    private String customerGroup;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
