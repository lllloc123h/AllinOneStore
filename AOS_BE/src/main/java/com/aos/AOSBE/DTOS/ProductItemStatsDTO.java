package com.aos.AOSBE.DTOS;

import com.aos.AOSBE.Entity.ProductItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemStatsDTO {
    private ProductItemsDTOS productItem;
    private Double rating;
    private Double returnAmount;
    private Double sumTotal;
    private Double sumCost;
}
