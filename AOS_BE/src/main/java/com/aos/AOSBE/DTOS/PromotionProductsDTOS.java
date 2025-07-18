package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionProductsDTOS {
    private int id;
    private int requireQty;
    private boolean isGift;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProductItemsDTOS productItem;
    private int promotionId;
}

