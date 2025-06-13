package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PromotionsService {
	@Autowired
    private PromotionsRepository promotionsRepository;
    private final PromotionProductRepository promotionProductRepository;

    public List<Promotions> promotionsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return promotionsRepository.findAll(pageable).getContent();
    }
    public Promotions promotionsSave(Promotions promotions) {
        return promotionsRepository.save(promotions);
    }
    public Optional<Promotions> promotionsFindById(int id) {
        return promotionsRepository.findById(id);
    }
    public void promotionsDeleteById(int id) {
        promotionsRepository.deleteById(id);
    }

    public double applyComboPromotion(List<Long> productItemIds, List<Integer> quantities) {
        List<Promotions> activeCombos = promotionRepository.findAllByTypeAndIsActive("COMBO", true);
        double totalComboDiscount = 0;

        for (Promotions promo : activeCombos) {
            List<PromotionProduct> requiredProducts = promotionProductRepository.findByPromotions(promo);
            boolean comboEligible = true;

            // kiểm tra coi có đủ số lượng để áp dụng combo 0
            for (PromotionProduct pp : requiredProducts) {
                int index = productItemIds.indexOf(pp.getProductItems().getId());
                if (index == -1 || quantities.get(index) < pp.getRequireQty()) {
                    comboEligible = false;
                    break;
                }
            }

            if (comboEligible) {
                double originalTotal = 0;
                for (PromotionProduct pp : requiredProducts) {
                    int index = productItemIds.indexOf(pp.getProductItems().getId());
                    double price = pp.getProductItems().getPrice(); // giả định có field getPrice
                    originalTotal += price * pp.getRequireQty();
                }

                totalComboDiscount += (originalTotal - promo.getComboPrice());
                // Nếu muốn áp dụng nhiều lần, tính toán số lần áp dụng dựa vào min(quantities / requireQty)
            }
        }

        return totalComboDiscount;
    }

    public double applyDiscountCoupon(String couponCode, double subtotal) {
        if (couponCode == null || couponCode.isBlank()) return 0;

        Promotions coupon = promotionRepository.findByNameAndTypeAndIsActive(couponCode, "DISCOUNT", true)
                .orElse(null);
        if (coupon == null) return 0;

        if (coupon.getStartAt().isAfter(LocalDateTime.now()) || coupon.getEndAt().isBefore(LocalDateTime.now()))
            return 0;

        return switch (coupon.getDiscountType()) {
            case "PERCENT" -> subtotal * coupon.getDiscountValue() / 100;
            case "FIXED" -> coupon.getDiscountValue();
            default -> 0.0;
        };
    }

    public double applyFreeshipCoupon(String couponCode, double shippingFee) {
        if (couponCode == null || couponCode.isBlank()) return shippingFee;

        Promotions freeship = promotionRepository.findByNameAndTypeAndIsActive(couponCode, "FREESHIP", true)
                .orElse(null);
        if (freeship == null) return shippingFee;

        if (freeship.getStartAt().isAfter(LocalDateTime.now()) || freeship.getEndAt().isBefore(LocalDateTime.now()))
            return shippingFee;

        return 0.0;
    }
}
