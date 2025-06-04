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
public class OrdersDTOS {
    private int id;
    private String shippingStatus;
    private double estimatedShippingFee;
    private String freeshipCouponCode;
    private double actualShippingFee;
    private String discountCouponCode;
    private double discountValue;
    private LocalDate shippedDate;
    private String paymentStatus;
    private String note;
    private int point;
    private double finalTotal;
    private String orderInfor;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private int accounts;
    private int paymentMethods;
    private int shippingMethods;
}
