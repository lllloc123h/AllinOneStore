package com.aos.AOSBE.DTOS;

import com.aos.AOSBE.Entity.CostHistories;
import com.aos.AOSBE.Entity.PriceHistories;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class DetailStatsDTO {
    private List<PromotionsDTOS> promotions;
    private List<PriceHistoriesDTOS> priceHistories;
    private List<CostHistoriesDTOS> costHistories;
    private List<TurnBuyDTO> data;


}
