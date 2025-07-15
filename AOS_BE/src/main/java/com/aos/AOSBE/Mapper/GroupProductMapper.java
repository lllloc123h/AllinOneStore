package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.DTOS.BaseProductsDTOS;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.BaseProducts;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
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
    @Autowired
    private PromotionProductsRepository promotionProductsRepository;
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

    public ProductItemsDTOS mapperToProductItemDTO(ProductItems entity,int promotionId) {
        return new ProductItemsDTOS(
                entity.getId(),
                entity.getPrice(),
                entity.getDescription(),
                handleListSkuToFilter.getDescriptionOfSku(entity.getSku()),
                entity.getQty(),
                promotionProductsRepository.findAllByProductItems_IdAndPromotions_Id(entity.getId(),promotionId).isGift()
        );
    }
}
