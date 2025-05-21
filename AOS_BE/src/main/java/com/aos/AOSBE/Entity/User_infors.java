package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_infors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_infors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email")
    private String email;
    @Column(name = "average_order_value")
    private double average_order_value;
    @Column(name = "user_rank")
    private String user_rank;
    @Column(name = "total_spent")
    private double total_spent;
    @Column(name = "total_order")
    private int total_order;
    @Column(name = "loyalty_point")
    private int loyalty_point;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
