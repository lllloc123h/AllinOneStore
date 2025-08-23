package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemStatsDTO {
    private ProductItemsDTOS productItem;
    private Double rating;
    private Double revenue;
    private Double discountOnProduct;
    private Double totalCost;
    private Integer countReturned;
    private Double exceptionCost;
}
