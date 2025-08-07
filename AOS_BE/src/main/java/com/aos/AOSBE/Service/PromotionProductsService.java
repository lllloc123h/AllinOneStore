package com.aos.AOSBE.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aos.AOSBE.DTOS.ForeCastDTO;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.DTOS.PromotionProductsDTOS;
import com.aos.AOSBE.Mapper.PromotionProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Repository.PromotionProductsRepository;

@Service
public class PromotionProductsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;

	@Autowired
	private PromotionProductsRepository promotionProductsRepository;
	@Autowired
	private PromotionProductsMapper promotionProductsMapper;

	public Optional<PromotionProducts> findById(int id) {
		return promotionProductsRepository.findById(id);
	}

	@Transactional
	public PromotionProducts save(PromotionProducts entity) {
		return promotionProductsRepository.save(entity);
	}

	@Transactional
	public void deleteById(int id) {
		promotionProductsRepository.deleteById(id);
	}

	public List<PromotionProducts> findPromotionProductsByPromotionId(int promotionId) {
		return promotionProductsRepository.findPromotionProductsByPromotionId(promotionId);
	}

	public Page<PromotionProducts> promotionsFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		return promotionProductsRepository.findAll(pageable);
	}

	public List<PromotionProducts> findByProductItemsId(int productItemId) {
		return promotionProductsRepository.findByProductItems_Id(productItemId);
	}

	public Page<PromotionProducts> promotionsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PromotionProducts> spec = specBuilder.buildFilter(filters);
		return promotionProductsRepository.findAll(spec, pageable);
	}

	public List<PromotionProducts> findPromotionProductsByPromotionsId(int promotionsId) {
		return promotionProductsRepository.findPromotionProductsByPromotions_Id(promotionsId);
	}

	public List<PromotionProducts> findAll() {
		return promotionProductsRepository.findAll();
	}

	public Boolean existCombo(List<PromotionProductsDTOS> list) {
		Map<Integer, List<PromotionProductsDTOS>> map = promotionProductsRepository.findActivePromotionProducts().stream()
				.map(promotionProductsMapper::mapper)
				.collect(Collectors.groupingBy(PromotionProductsDTOS::getPromotionId));
		if (list.get(0).getPromotionId() != 0){
			map.remove(list.get(0).getPromotionId()); // Lọc nếu có truyền promotionId vào
		}
		// Lọc các mục trong map mà kích thước của danh sách bằng kích thước của list
		Map<Integer, List<PromotionProductsDTOS>> map2 = map.entrySet().stream()
				.filter(e -> e.getValue().size() == list.size())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
// Kiểm tra xem có bất kỳ mục nào trong map2 có danh sách chứa tất cả các phần tử trong list (so sánh productItem.id và requireQty, không quan tâm thứ tự)
		return map2.values().stream()
				.anyMatch(promotionProductsDTOS ->
						promotionProductsDTOS.size() == list.size() &&
								list.stream().allMatch(
										item1 ->
												promotionProductsDTOS.stream().
														anyMatch(item2 -> item1.getProductItem().getId() == item2.getProductItem().getId() &&
																item1.getRequireQty() == item2.getRequireQty()
														)
								)
				);
	}

	public List<Map<String, Object>> findDiscountedProductsNative() {
		return promotionProductsRepository.findDiscountedProductsNative();
	}
}
