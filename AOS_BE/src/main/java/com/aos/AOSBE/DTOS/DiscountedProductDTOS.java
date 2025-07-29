package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountedProductDTOS {
    private Long productItemId;
    private String productName;
    private Double originalPrice;
    private Double discountValue;
    private String promotionName;
    private Double discountedPrice;
    private String imageUrl;
}
