package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class CheckToCreateComboDTO {
    private List<PromotionProductsDTOS> listToAdd;
    private PromotionsDTOS promotion;
}
