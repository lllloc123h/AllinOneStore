package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDetailDTO {
    private int qty;
    private double sellingPrice;
    private double total;
    private boolean gift;
    private String sku;
    private String productName;
    private String description;
}
