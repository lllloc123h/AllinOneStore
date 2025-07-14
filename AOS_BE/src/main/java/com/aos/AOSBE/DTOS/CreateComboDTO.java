package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateComboDTO {
    private Integer cartId;
    private List<Items> items;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Items {
        private Integer itemId;
        private Integer quantity;
        private Integer promotionId;
    }
}


