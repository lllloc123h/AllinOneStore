package com.aos.AOSBE.DTOS;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {
    private OrdersDTOS order;
    private List<OrderItemDetailDTO> items;
    private AccountsDTOS account;
}
