package com.aos.AOSBE.DTOS;

import java.util.List;

import lombok.Data;

@Data
public class PromotionSendRequestDTOS {
    private List<Integer> userIds;
    private String couponCode;
}
