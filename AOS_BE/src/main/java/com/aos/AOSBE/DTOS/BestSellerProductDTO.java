package com.aos.AOSBE.DTOS;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellerProductDTO {
    private int productItemId;
    private int baseProductId;
    private String name;
    private String material;
    private String imageUrl;
    private double price;
    private int qty;
    private int turnBuy;
}
