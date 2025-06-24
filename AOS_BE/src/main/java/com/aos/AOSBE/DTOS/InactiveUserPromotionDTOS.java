package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InactiveUserPromotionDTOS {
    private int userId;
    private String email;
    private String reason; // Ví dụ: "Không có hoạt động trong 30 ngày"
    private String suggestedCouponCode;
}
