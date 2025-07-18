package com.aos.AOSBE.Mapper;

import java.time.LocalDateTime;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PromotionProductsMapper {

    public PromotionProductsDTOS mapper(PromotionProducts entity) {
        PromotionProductsDTOS dto = new PromotionProductsDTOS();
        dto.setId(entity.getId());
        dto.setRequireQty(entity.getRequireQty());
        dto.setGift(entity.isGift());
        dto.setCostShare(entity.getCostShare());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // Map ProductItems sang ProductItemsDTOS
        if (entity.getProductItems() != null) {
            ProductItems p = entity.getProductItems();

            ProductItemsDTOS productDto = new ProductItemsDTOS(
            );

            dto.setProductItem(productDto);
        }

        // Map Promotion ID
        if (entity.getPromotions() != null) {
            dto.setPromotionId(entity.getPromotions().getId());
        }

        return dto;
    }
}
