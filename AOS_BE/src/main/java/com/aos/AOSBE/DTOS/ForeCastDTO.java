package com.aos.AOSBE.DTOS;

import com.aos.AOSBE.Entity.PromotionProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForeCastDTO {
    private int productItemId;
    private String name;
    private String category;
    private double price;
    private double cost;
    private Integer stockQty;
    private Integer turnBuy;
    private Double avgRatingLast30Days;
    private Integer reviewCountLast30Days;
    private Double returnRateLast30Days;
    private Integer soldLast30Days;
    private List<PromotionsDTOS> inPromotions;
    private List<PromotionProductsDTOS> giftUsageLast30Days;
    // Assuming PriceHistoryDTO is another DTO class you have defined
    private List<PriceHistoriesDTOS> priceHistoriesLast30Days;
    private List<CostHistoriesDTOS> costHistoriesLast30Days;
//    viewCountLast30Days
//addToCartCountLast30Days
//wishlistCountLast30Days
//conversionRateLast30Days (tỷ lệ từ xem → mua)
//refundAmountLast30Days (nếu cần dự đoán tổn thất do hoàn trả)
}
