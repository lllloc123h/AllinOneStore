package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfileDTO {
    private String fullname;
    private String email;
    private String phone;
    private String avatarUrl;
    private double averageOrderValue;
    private String userRank;
    private double totalSpent;
    private int totalOrder;
    private int loyaltyPoint;
}
