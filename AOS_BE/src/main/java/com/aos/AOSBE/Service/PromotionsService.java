package com.aos.AOSBE.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aos.AOSBE.Repository.OrderItemsRepository;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Repository.PromotionsRepository;

@Service
public class PromotionsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private PromotionsRepository promotionsRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;

	public Page<Promotions> promotionsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Promotions> spec = specBuilder.buildFilter(filters);
		return promotionsRepository.findAll(spec, pageable);
	}

	@Transactional
	public Promotions promotionsSave(Promotions promotions) {
		return promotionsRepository.save(promotions);
	}

	public Optional<Promotions> promotionsFindById(int id) {
		return promotionsRepository.findById(id);
	}

	@Transactional
	public void promotionsDeleteById(int id) {
		if(orderItemsRepository.findByPromotionId(id).size() > 0) {
			throw new RuntimeException("Ưu đãi đã được áp dụng cho đơn hàng, không thể xóa.");
		}
		promotionsRepository.deleteById(id);
	}

	public List<Promotions> promotionsFindByIsActiveTrueByPromotionItemId(int productItemId) {
		return promotionsRepository.findActivePromotionsByProductItemId(productItemId);
	}
	public Object[] promotionsFindFirstTypePromotionByProductItemId(int productItemId) {
		return promotionsRepository.findFirstTypePromotionByProductItemId(productItemId);
	}
	public List<Promotions> findPromotionByProductItemAndDuration(int productItemId, LocalDateTime startAt, LocalDateTime endAt) {
		return promotionsRepository.findPromotionsByDuration(productItemId, startAt, endAt);
	}
	public List<Promotions> findActivePromotionsByBaseProductId(int baseId) {
		return promotionsRepository.findActivePromotionsByBaseProductId(baseId);
	}

	public boolean isPromotionValid(Promotions promotion) {
        if (promotion == null) return false;

        LocalDateTime now = LocalDateTime.now();

        if (!promotion.isActive()) return false;
        if (promotion.getStartAt() != null && promotion.getStartAt().isAfter(now)) return false;
        if (promotion.getEndAt() != null && promotion.getEndAt().isBefore(now)) return false;
        if (promotion.getQty() <= 0) return false;

        return true;
    }
	
	public Promotions findActivePromotionForItem(int productItemId) {
	    return promotionsRepository.findBestActivePromotionByProductItemId(productItemId);
	}

}