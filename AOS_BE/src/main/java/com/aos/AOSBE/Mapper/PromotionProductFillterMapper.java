package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.DTOS.PromotionProductFillterDTO;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionProductFillterMapper {
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private HandleListSkuToFilter handleListSkuToFilter;
    @Autowired
    private ProductImagesRepository productImagesRepository;
    @Autowired
    private PromotionProductsRepository promotionProductsRepository;
    public PromotionProductFillterDTO mapper(ProductItems entity) {
        Double rating = reviewsRepository.findAverageRatingByProductItemId(entity.getId());
        List<ProductImages> imageUrl = productImagesRepository.findByProductItemsId(entity.getId());
        List<PromotionProducts> inCombo = promotionProductsRepository.findActivePromotionProductsByProductItemsId(entity.getId());
        return new PromotionProductFillterDTO(
            entity.getId(),
            entity.getCost(),
            entity.getPrice(),
               rating == null ? 0.0 : rating,
                entity.getTurnBuy(),
                handleListSkuToFilter.getDescriptionOfSku(entity.getSku()),
                entity.getSafetyStock(),
                entity.getQty(),
                imageUrl == null || imageUrl.isEmpty() ? "" : imageUrl.get(0).getImageUrl(),
                entity.isActive(),
               inCombo == null ? 0 : inCombo.size()
        );
    }
//       private double cost;
//    private double price;
//    private int turnBuy;
//    private String sku;
//    private int safetyStock;
//    private int qty;
//    private String imageUrl;
//    private boolean active;
}
