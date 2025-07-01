package com.aos.AOSBE.Repository;

import java.util.List;

import com.aos.AOSBE.DTOS.OrderItemDetailDTO;
import com.aos.AOSBE.DTOS.OrdersDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {
    private OrdersDTOS order;
    private List<OrderItemDetailDTO> items;
}
