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
public class AccountsDTOS {
    private int id;
    private String email;
    private String password;
    private String fullname;
    private String avatar;
    private String phone;
    private double averageOrderValue;
    private String userRank;
    private double totalSpent;
    private int totalOrder;
    private int loyaltyPoint;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
