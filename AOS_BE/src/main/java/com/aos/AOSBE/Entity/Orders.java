package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
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
    private String account_email;
    @Column(name = "shipping_status")
    private String shipping_status;
    @Column(name = "shipping_fee")
    private double shipping_fee;
    @Column(name = "point")
    private int point;
    @Column(name = "total")
    private double total;
    @Column(name = "shipping_infor")
    private String shipping_infor;
    @Column(name = "shipped_date")
    private LocalDateTime shipped_date;
    @Column(name = "payment_status")
    private String payment_status;
    @Column(name = "payment_method")
    private String payment_method;
    @Column(name = "shipping_method")
    private String shipping_method;
    @Column(name = "note")
    private String note;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
