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
    @Column(name = "account_email")
    private String accountEmail;
    @Column(name = "shipping_status")
    private String shippingStatus;
    @Column(name = "shipping_fee")
    private double shippingFee;
    @Column(name = "point")
    private int point;
    @Column(name = "total")
    private double total;
    @Column(name = "shipping_infor")
    private String shippingInfor;
    @Column(name = "shipped_date")
    private LocalDateTime shippedDate;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "shipping_method")
    private String shippingMethod;
    @Column(name = "note")
    private String note;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ProductItems productProductItems;





}
