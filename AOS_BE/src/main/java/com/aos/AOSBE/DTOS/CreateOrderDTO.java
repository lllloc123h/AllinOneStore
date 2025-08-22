package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDTO {
    private OrdersDTOS order;
    private List<CheckoutCustomDTO> customs;
}
