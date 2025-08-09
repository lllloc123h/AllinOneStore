package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class CheckComboDTO {
    private List<PromotionProductsDTOS> listToAdd;
    private List<PromotionProductsDTOS> listToDelete;
    private PromotionsDTOS promotion;
}
