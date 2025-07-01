package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTOS {
    private ProductItemsDTOS productItem;
    private List<ProductImagesDTOS> productImages;
    private List<PriceHistoriesDTOS> priceHistories;
    private List<PromotionProductDTOS> promotionProducts;
}