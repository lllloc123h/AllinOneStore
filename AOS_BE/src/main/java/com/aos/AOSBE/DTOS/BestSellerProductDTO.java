package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellerProductDTO {
    private int id;
    private String name;
    private String imageUrl;
    private int turnBuy;
    private int rating;
    private double price;
    private Long totalTurnBuy; // ✅ Đổi từ int sang Long
}