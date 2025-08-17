package com.aos.AOSBE.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aos.AOSBE.DTOS.CheckComboDTO;
import com.aos.AOSBE.DTOS.CheckToCreateComboDTO;
import com.aos.AOSBE.DTOS.PromotionProductsDTOS;
import com.aos.AOSBE.Mapper.PromotionProductsMapper;
import com.aos.AOSBE.Mapper.PromotionsMapper;
import com.aos.AOSBE.Repository.OrderItemsRepository;
import com.aos.AOSBE.Repository.PromotionsRepository;
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
	@Autowired
	private PromotionsRepository promotionsRepository;
	@Autowired
	private PromotionsMapper promotionsMapper;
	@Autowired
	private OrderItemsRepository orderItemsRepository;

	public Optional<PromotionProducts> findById(int id) {
		return promotionProductsRepository.findById(id);
	}
	public List<Map<String, Object>> findDiscountedProductsNative(){
		return promotionProductsRepository.findDiscountedProductsNative();
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
	public String existCombo(CheckToCreateComboDTO checkToCreateComboDTO) {
		//nhóm lại theo promotion ID
		Map<Integer, List<PromotionProducts>> map = promotionProductsRepository.findActivePromotionProducts().stream()
				.collect(Collectors.groupingBy(p -> p.getPromotions().getId()));
// loại ra promotion ID của bản thân ( nếu có )
		if (checkToCreateComboDTO.getListToAdd().get(0).getPromotionId() != null) {
			map.remove(checkToCreateComboDTO.getListToAdd().get(0).getPromotionId()); // Lọc nếu có truyền promotionId vào
		}
		// Lọc các mục trong map mà kích thước của danh sách bằng kích thước của list
		Map<Integer, List<PromotionProducts>> map2 = map.entrySet().stream()
				.filter(e -> e.getValue().size() == checkToCreateComboDTO.getListToAdd().size())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		LocalDateTime endDate = checkToCreateComboDTO.getPromotion().getEndAt();
		LocalDateTime startDate = checkToCreateComboDTO.getPromotion().getStartAt();

		// Kiểm tra combo trùng lặp
		for (Map.Entry<Integer, List<PromotionProducts>> entry : map2.entrySet()) {
			List<PromotionProducts> promotionProducts = entry.getValue();

			boolean isDuplicate = promotionProducts.size() == checkToCreateComboDTO.getListToAdd().size() &&
					checkToCreateComboDTO.getListToAdd().stream().allMatch(
							item1 -> promotionProducts.stream().anyMatch(
									item2 -> item1.getProductItem().getId() == item2.getProductItems().getId() &&
											item1.getRequireQty() == item2.getRequireQty()
							)
					);

			if (isDuplicate) {
				LocalDateTime existingStart = promotionProducts.get(0).getPromotions().getStartAt();
				LocalDateTime existingEnd = promotionProducts.get(0).getPromotions().getEndAt();
				if (startDate.isBefore(existingEnd) && existingStart.isBefore(endDate)) {
					// Kiểm tra chồng chéo thời gian
					System.err.println("DUPLICATE_COMBO:" + entry.getKey());
					return "Không được tạo cùng sản phẩm và khoảng thời gian chồng chéo nhau với ID: " + entry.getKey();
				}

			}
		}
		System.out.println("NO_CONFLICT");
		return "NO_CONFLICT";
	}

	@Transactional
	public String existComboForUpdate(CheckComboDTO checkComboDTO) {
		// nếu list có thì xóa
		List<PromotionProductsDTOS> listToDelete = checkComboDTO.getListToDelete();
		List<PromotionProductsDTOS> listToAdd = checkComboDTO.getListToAdd();
		if (orderItemsRepository.findByPromotionId(checkComboDTO.getPromotion().getId()).size() > 0) {
			promotionsRepository.save(promotionsMapper.mapperToObject(checkComboDTO.getPromotion()));
			return "Đã cập nhật thông tin ưu đãi, nhưng không thể cập nhật sản phẩm trong combo vì đã có đơn hàng sử dụng ưu đãi này.";
		}
		// kiểm tra xem có tồn tại promotion không
		promotionsRepository.save(promotionsMapper.mapperToObject(checkComboDTO.getPromotion()));
		if ( listToDelete != null && !listToDelete.isEmpty() ) {
		for (PromotionProductsDTOS dto : listToDelete) {
		promotionProductsRepository.deleteById(dto.getId());
		}
		}
// list to add luôn có
		for (PromotionProductsDTOS dto : listToAdd) {
			promotionProductsRepository.save(promotionProductsMapper.mapperToObject(dto));
		}
		CheckToCreateComboDTO checkToCreateComboDTO = new CheckToCreateComboDTO();
		checkToCreateComboDTO.setListToAdd(listToAdd);
		checkToCreateComboDTO.setPromotion(promotionsMapper.mapper(promotionsRepository.findById(listToAdd.get(0).getPromotionId()).get()));
		String isExist = existCombo(checkToCreateComboDTO);
		if (isExist != "NO_CONFLICT") {
			throw new RuntimeException(isExist);
		}
		return isExist;
	}
}
