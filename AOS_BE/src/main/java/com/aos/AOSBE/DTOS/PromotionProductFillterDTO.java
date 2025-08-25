package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionProductFillterDTO {
    private int idProductItem;
    private double cost;
    private double price;
    private Double rating;
    private int turnBuy;
    private String sku;
    private UUID searchPoint;
    private int qty;
    private String imageUrl;
    private boolean active;
    private List<PromotionsDTOS> inPromotions;
}
