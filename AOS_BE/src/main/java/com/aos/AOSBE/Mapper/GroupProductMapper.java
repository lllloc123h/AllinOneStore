package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.DTOS.BaseProductsDTOS;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Service.BaseProductsService;
import com.aos.AOSBE.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupProductMapper {
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private HandleListSkuToFilter handleListSkuToFilter;
    public BaseProductsDTOS mapperToBaseProductsDTOS(BaseProducts entity) {
        return new BaseProductsDTOS(entity.getId(),
                entity.getName(),
                entity.getMaterial(),
                entity.getMainImageUrl(),
                entity.isCustom(),
                entity.getTurnBuy(),
                entity.getRating()
                ,entity.isActive()
                ,entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCategories().getName());
    }

    public ProductItemsDTOS mapperToProductItemDTO(ProductItems entity) {
        return new ProductItemsDTOS(
                entity.getId(),
                entity.getCost(),
                entity.getPrice(),
                entity.getTurnBuy(),
                entity.getDescription(),
                handleListSkuToFilter.getDescriptionOfSku(entity.getSku()),
                entity.getSafetyStock(),
                entity.getQty(),
                entity.getSellStart(),
                entity.getSellEnd(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getBaseProducts().getId()
        );
    }
}
