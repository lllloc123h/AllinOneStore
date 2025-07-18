package com.aos.AOSBE.DTOS;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountedProductDTOS {
    private int productItemId;
    private String baseProductName;
    private BigDecimal price;
    private String discountType;
    private String promotionName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String imageUrl;

    // Constructor PUBLIC và đúng thứ tự tham số, KHỚP 100% với JPQL
    public DiscountedProductDTOS(
        int productItemId,
        String baseProductName,
        BigDecimal price,
        String discountType,
        String promotionName,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String imageUrl
    ) {
        this.productItemId = productItemId;
        this.baseProductName = baseProductName;
        this.price = price;
        this.discountType = discountType;
        this.promotionName = promotionName;
        this.startAt = startAt;
        this.endAt = endAt;
        this.imageUrl = imageUrl;
    }
}
