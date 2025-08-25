package com.aos.AOSBE.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.DTOS.ForeCastDTO;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Mapper.CostHistoriesMapper;
import com.aos.AOSBE.Mapper.PriceHistoriesMapper;
import com.aos.AOSBE.Mapper.ProductItemsMapper;
import com.aos.AOSBE.Mapper.PromotionProductsMapper;
import com.aos.AOSBE.Mapper.PromotionsMapper;
import com.aos.AOSBE.Repository.CostHistoriesRepository;
import com.aos.AOSBE.Repository.OrderItemsRepository;
import com.aos.AOSBE.Repository.PriceHistoriesRepository;
import com.aos.AOSBE.Repository.ProductItemsRepository;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Repository.PromotionsRepository;
import com.aos.AOSBE.Repository.ReturnsRepository;
import com.aos.AOSBE.Repository.ReviewsRepository;

@Service
public class ProductItemsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;

	@Autowired
	private ProductItemsRepository productItemsRepository;

	@Autowired
	private ProductItemsMapper productItemsMapper;

	@Autowired
	private ReviewsRepository reviewsRepository;

	@Autowired
	private ReturnsRepository returnsRepository;

	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Autowired
	private PromotionsRepository promotionsRepository;

	@Autowired
	private PromotionsMapper promotionsMapper;

	@Autowired
	private PromotionProductsRepository promotionProductsRepository;

	@Autowired
	private PromotionProductsMapper promotionProductsMapper;

	@Autowired
	private CostHistoriesMapper costHistoriesMapper;

	@Autowired
	private PriceHistoriesMapper priceHistoriesMapper;

	@Autowired
	private CostHistoriesRepository costHistoriesRepository;

	@Autowired
	private PriceHistoriesRepository priceHistoriesRepository;
	@Autowired
	private QdrantService qdrantService;

	public Page<ProductItems> productItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ProductItems> spec = specBuilder.buildFilter(filters);
		return productItemsRepository.findAll(spec, pageable);
	}

	public List<ProductItems> productItemsFindByBaseProductId(int id) {
		return productItemsRepository.findByBaseProductsId(id);
	}

	public List<ProductItems> productItemsByActiveIsTrue(int id) {
		return productItemsRepository.findByBaseProductsId(id);
	}

	public List<ProductItems> productItemsFindByBaseProductIdTheActiveTrue(int id) {
		return productItemsRepository.findByBaseProductsIdWithTheActiveTrue(id);
	}

	public List<ProductItems> productItemsFindAllHaveSkuLike(String skuLike) {
		String skuLikeSplit[] = skuLike.split("-");
		System.out.println(skuLikeSplit);
		String skuLikeConvert = "%" + skuLikeSplit[0] + "%" + skuLikeSplit[1];
		return productItemsRepository.findBySkuLike(skuLikeConvert);
	}

//	public List<ProductItems> productItemsFindAllBoughtByGender(boolean gender) {
//
//		return productItemsRepository.findAllBoughtByGender(gender);
//	}

	public Page<Object[]> productItemsFilterItemsByColorAndSizePriceMinAndPriceMax(int page, int size,
			String skuColorLike, String skuSizeLike, String minPrice, String maxPrice, String categories,
			String keyWord, Integer idProductItem) {
		Pageable pageable = PageRequest.of(page, size);
		int isMinPriceEmpty = (minPrice == null || minPrice.isEmpty()) ? 1 : 0;
		int isMaxPriceEmpty = (maxPrice == null || maxPrice.isEmpty()) ? 1 : 0;
		int isCategoriesEmpty = (categories == null || categories.isEmpty()) ? 1 : 0;
		int isKeyWordEmpty = (keyWord == null || keyWord.isEmpty()) ? 1 : 0;
		int idProductItemIsEmpty = (idProductItem == null) ? 1 : 0;

		skuColorLike = (skuColorLike == null) ? "" : skuColorLike;
		categories = (categories == null) ? "" : categories;
		skuSizeLike = (skuSizeLike == null) ? "" : skuSizeLike;
		minPrice = (minPrice == null) ? "" : minPrice;
		maxPrice = (maxPrice == null) ? "" : maxPrice;
		HandleListSkuToFilter buildKey = new HandleListSkuToFilter();
		String skuList = buildKey.buildKeyFilter(skuColorLike, skuSizeLike);
		int isSkuLikeListEmpty = (skuList == null || skuList.isEmpty()) ? 1 : 0;

		try {
			return productItemsRepository.newFilterItems(pageable, isSkuLikeListEmpty, skuList, isMinPriceEmpty,
					minPrice, isMaxPriceEmpty, maxPrice, isCategoriesEmpty, categories, isKeyWordEmpty, keyWord,
					idProductItemIsEmpty, idProductItem != null ? idProductItem.intValue() : 0);
		} catch (Exception e) {
			e.printStackTrace();
			return Page.empty();
		}
	}

	@Transactional
	public ProductItems productItemsSave(ProductItems productItems) {
		return productItemsRepository.save(productItems);
	}

	public Optional<ProductItems> productItemsFindById(int id) {
		return productItemsRepository.findById(id);
	}

	@Transactional
	public void productItemsDeleteById(int id) {
		if (!orderItemsRepository.findAllByProductItemId(id).isEmpty()) {
			throw new RuntimeException("Sản phẩm đã được mua, không thể xóa.");
		}
		qdrantService.deleteDocumentByPayloadProductItemId(id);
		productItemsRepository.deleteById(id);
	}

	@Transactional
	public List<ProductItemsDTOS> getRelatedProductItems(int id) {
		ProductItems currentItem = productItemsRepository.findById(id).orElse(null);
		if (currentItem == null)
			return new ArrayList<>();

		Long categoryId = (long) currentItem.getBaseProducts().getCategories().getId();
		Long productId = (long) id;

		List<ProductItems> relatedItems = productItemsRepository
				.findRelatedItems(categoryId, productId, PageRequest.of(0, 4)).getContent();

		return relatedItems.stream().map(productItemsMapper::mapper).collect(Collectors.toList());
	}

	@Transactional
	public List<ProductItems> findAllDiscountedProductIds() {
		return productItemsRepository.findAllDiscountedProductIds();
	}

	public ForeCastDTO getForeCastDataLast30Days(int productItemId) {
		ForeCastDTO foreCastDTO = new ForeCastDTO();
		ProductItems result = productItemsRepository.findById(productItemId).get();
		if (result != null) {
			foreCastDTO.setProductItemId(result.getId());
			foreCastDTO.setCreatedAt(result.getCreatedAt().toString());
			foreCastDTO.setName(result.getBaseProducts().getName());
			foreCastDTO.setCategory(result.getBaseProducts().getCategories().getName());
			foreCastDTO.setCost(result.getCost());
			foreCastDTO.setPrice(result.getPrice());
			foreCastDTO.setStockQty(result.getQty());
			foreCastDTO.setTurnBuy(result.getTurnBuy());
			foreCastDTO.setAvgRatingLast30Days(reviewsRepository.findAverageRatingByProductItemIdAndCreateAtBetween(
					result.getId(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
			foreCastDTO.setReviewCountLast30Days(reviewsRepository.countReviewsByProductItemIdAndCreateAtBetween(
					result.getId(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
			Integer returnCountLast30Days = returnsRepository.findReturnsByProductItemIdAndCreateAtBetween(
					result.getId(), LocalDateTime.now().minusDays(30), LocalDateTime.now());
			double orderCountLast30Days = orderItemsRepository.sumQuantityByProductIdAndDateRange(result.getId(),
					LocalDateTime.now().minusDays(30), LocalDateTime.now());
			double returnRate = returnCountLast30Days == null ? 0.0
					: (Double.parseDouble(returnCountLast30Days + "") / orderCountLast30Days) * 100;
			foreCastDTO.setReturnRateLast30Days(returnRate);
			foreCastDTO.setSoldLast30Days((int) orderCountLast30Days);
			foreCastDTO.setInPromotions(promotionsRepository
					.findPromotionsByDuration(result.getId(), LocalDateTime.now().minusDays(30), LocalDateTime.now())
					.stream().map(promotionsMapper::mapper).toList());
			// foreCastDTO.setComboUsageLast30Days(promotionProductsRepository
			// .countPromotionProductsByProductItemsIdAndPromotionsStartAtAfterOrPromotionsEndAtBefore(result.getId(),
			// LocalDateTime.now().minusDays(30), LocalDateTime.now()));
			foreCastDTO.setGiftUsageLast30Days(promotionProductsRepository
					.findPromotionProductsByProductItems_IdAndGiftIsTrue(result.getId(), true).stream()
					.map(promotionProductsMapper::mapper).toList());
			foreCastDTO.setCostHistoriesLast30Days(costHistoriesRepository
					.findCostHistoriesByProductItems_IdAndCreatedAtBetween(result.getId(),
							LocalDateTime.now().minusDays(30), LocalDateTime.now())
					.stream().map(costHistoriesMapper::mapper).toList());
			foreCastDTO.setPriceHistoriesLast30Days(priceHistoriesRepository
					.findPriceHistoriesByProductItems_IdAndCreatedAtBetween(result.getId(),
							LocalDateTime.now().minusDays(30), LocalDateTime.now())
					.stream().map(priceHistoriesMapper::mapper).toList());
			return foreCastDTO;
		}
		return null;
	}
}
