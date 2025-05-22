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
    @Column(name = "created_by_employee_id")
    private int createdByEmployeeId;
    @Column(name = "shipping_status")
    private String shippingStatus;
    @Column(name = "shipping_fee")
    private double shippingFee;
    @Column(name = "point")
    private int point;
    @Column(name = "total")
    private double total;
    @Column(name = "order_infor")
    private String orderInfor;
    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "shipping_method_id")
    private int shippingMethodId;
    @Column(name = "note")
    private String note;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Accounts accounts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentPaymentMethods;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private ShippingMethods shippingShippingMethods;





}
