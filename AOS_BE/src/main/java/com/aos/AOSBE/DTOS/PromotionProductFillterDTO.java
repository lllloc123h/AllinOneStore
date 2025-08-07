package com.aos.AOSBE.DTOS;

import com.aos.AOSBE.Entity.BaseProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private int safetyStock;
    private int qty;
    private String imageUrl;
    private boolean active;
    private int inCombo;
}
