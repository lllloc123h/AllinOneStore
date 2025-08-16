package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionProductCheckDTO {
    private int id;
    private int requireQty;
    private boolean isGift;
    private String giftOption;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int productItem;
    private int promotionId;
}
