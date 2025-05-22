package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_infors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfors {
    @Id
    
    @Column(name = "email")
    private String email;
    @Column(name = "average_order_value")
    private double averageOrderValue;
    @Column(name = "user_rank")
    private String userRank;
    @Column(name = "total_spent")
    private double totalSpent;
    @Column(name = "total_order")
    private int totalOrder;
    @Column(name = "loyalty_point")
    private int loyaltyPoint;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_email")
    private Accounts accounts;





}
