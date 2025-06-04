package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "shipping_status")
    private String shippingStatus;
    @Column(name = "estimated_shipping_fee")
    private double estimatedShippingFee;
    @Column(name = "freeship_coupon_code")
    private String freeshipCouponCode;
    @Column(name = "actual_shipping_fee")
    private double actualShippingFee;
    @Column(name = "discount_coupon_code")
    private String discountCouponCode;
    @Column(name = "discount_value")
    private double discountValue;
    @Column(name = "shipped_date")
    private LocalDate shippedDate;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "note")
    private String note;
    @Column(name = "point")
    private int point;
    @Column(name = "final_total")
    private double finalTotal;
    @Column(name = "order_infor")
    private String orderInfor;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Accounts accounts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentMethods;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_method_id")
    private ShippingMethods shippingMethods;





}
