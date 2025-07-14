package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDetailDTO {
    private ProductItemsDTOS productItem;
    private List<ProductImagesDTOS> images;
    private List<PriceHistoriesDTOS> priceHistories;
    private List<PromotionsDTOS> promotions;
}
