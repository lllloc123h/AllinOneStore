package com.aos.AOSBE.API;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.DiscountedProductDTOS;
import com.aos.AOSBE.DTOS.PriceHistoriesDTOS;
import com.aos.AOSBE.DTOS.ProductImagesDTOS;
import com.aos.AOSBE.DTOS.ProductItemDetailDTO;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.DTOS.PromotionsDTOS;
import com.aos.AOSBE.DTOS.filterAdvanceDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Mapper.PriceHistoriesMapper;
import com.aos.AOSBE.Mapper.ProductImagesMapper;
import com.aos.AOSBE.Mapper.ProductItemsMapper;
import com.aos.AOSBE.Mapper.PromotionsMapper;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Service.PriceHistoriesService;
import com.aos.AOSBE.Service.ProductImagesService;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.PromotionsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductItemsAPI {
	@Autowired
	private ProductItemsService productItemsService;

	@Autowired
	private ProductItemsMapper productItemsMapper;

	@Autowired
	private ProductImagesService productImagesService;

	@Autowired
	private ProductImagesMapper productImagesMapper;

	@Autowired
	private PriceHistoriesService priceHistoriesService;

	@Autowired
	private PriceHistoriesMapper priceHistoriesMapper;

	@Autowired
	private PromotionsService promotionsService;

	@Autowired
	private PromotionsMapper promotionsMapper;
	@Autowired
	private PromotionProductsRepository promotionProductsRepository;

	@GetMapping("/admin/ProductItems")
	public ResponseEntity<?> getAllProductItemsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<ProductItems> pageResult = productItemsService.productItemsFindAll(page, size, filters);
		List<ProductItemsDTOS> productItems = pageResult.getContent().stream().map(productItemsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", productItems);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/ProductItems/{id}")
	public ResponseEntity<ProductItems> getProductItemsByIdApi(@PathVariable int id) {
		ProductItems productItems = (ProductItems) productItemsService.productItemsFindById(id)
				.orElse(new ProductItems());
		return ResponseEntity.ok(productItems);
	}

	@GetMapping("/admin/ProductItems/ByBaseProductId/{id}")
	public ResponseEntity<?> getProductItemsByBaseProductIdIdApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "1000") int size, @PathVariable int id) {
		try {
			List<ProductItems> productItems = productItemsService.productItemsFindByBaseProductId(page, size, id)
					.getContent();
			List<ProductItemsDTOS> content = new ArrayList<>();
			for (ProductItems item : productItems) {
				content.add(productItemsMapper.mapper(item));
			}
			Map<String, Object> response = new HashMap();
			response.put("content", content);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("Message", "Đã có lỗi xảy ra" + e.getMessage()));
		}
	}

	@PostMapping("/admin/ProductItems")
	public ResponseEntity<?> addNewProductItems(@RequestBody ProductItemsDTOS entity) {
		try {

			ProductItems saved = productItemsService.productItemsSave(productItemsMapper.mapperToObject(entity));
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}

	}

	@PutMapping("/admin/ProductItems")
	public ResponseEntity<ProductItems> updateProductItems(@RequestBody ProductItems entity) {
		ProductItems updated = productItemsService.productItemsSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/ProductItems/{id}")
	public ResponseEntity<Void> deleteProductItems(@PathVariable int id) {
		productItemsService.productItemsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/Product/{sku}")
	public ResponseEntity<?> getAllProductItemsHaveSkuLikeApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @PathVariable String sku) {

		try {
			List<ProductItemsDTOS> productItems = new ArrayList<ProductItemsDTOS>();
			productItemsService.productItemsFindAllHaveSkuLike(sku).forEach(e -> {
				productItems.add(productItemsMapper.mapper(e));
			});
			return ResponseEntity.ok(productItems);
		} catch (Exception e) {

			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}

	}

	// PromotionProductController.java
	@GetMapping("/discounted-products")
	public ResponseEntity<List<DiscountedProductDTOS>> getDiscountedProducts() {
		LocalDateTime now = LocalDateTime.now();
		List<Map<String, Object>> rawResults = promotionProductsRepository.findDiscountedProductsNative();

		List<DiscountedProductDTOS> dtoList = rawResults.stream()
				.map(map -> new DiscountedProductDTOS(((Number) map.get("productItemId")).longValue(),
						(String) map.get("productName"), ((Number) map.get("originalPrice")).doubleValue(),
						((Number) map.get("discountValue")).doubleValue(), (String) map.get("promotionName"),
						((Number) map.get("discountedPrice")).doubleValue(), (String) map.get("imageUrl")))
				.collect(Collectors.toList());

		return ResponseEntity.ok(dtoList);
	}

	@GetMapping("/Product/MultiplrFilter")
	public ResponseEntity<?> getAllProductItemsByMultipleSkuChoices(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam("skuColorLikeReq") String skuColorLikeReq,
			@RequestParam("skuSizeLikeReq") String skuSizeLikeReq, @RequestParam("minPriceReq") String minPriceReq,
			@RequestParam("maxPriceReq") String maxPriceReq, @RequestParam("categories") String categories,
			@RequestParam("keyWord") String keyWord, @RequestParam(required = false) Integer idProductItem) {
		try {
			Page<Object[]> pageResult = productItemsService.productItemsFilterItemsByColorAndSizePriceMinAndPriceMax(
					page, size, skuColorLikeReq, skuSizeLikeReq, minPriceReq, maxPriceReq, categories, keyWord,
					idProductItem);
			List<filterAdvanceDTOS> productItems = new ArrayList<filterAdvanceDTOS>();
			pageResult.getContent().forEach(e -> {
				filterAdvanceDTOS item = new filterAdvanceDTOS();
				item.setId((int) e[0]);
				item.setProductItemId((int) e[1]);
				item.setName((String) e[2]);
				item.setMaterial((String) e[3]);
				item.setCategoryId((int) e[4]);
				item.setMainImage((String) e[5]);
				item.setCustom((boolean) e[6]);
				item.setTurnBuy((int) e[7]);
				item.setSku((String) e[8]);
				item.setRating((int) e[9]);
				item.setActive((boolean) e[10]);
				item.setQty((int) e[11]);
				item.setPrice(((Number) e[12]).doubleValue());
				item.setSafetyStock(((int) e[13]));
				productItems.add(item);
			});

			Map<String, Object> response = new HashMap<>();
			response.put("content", productItems);
			response.put("totalPages", pageResult.getTotalPages());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}

	}

	@GetMapping("/ProductItems/detail/{id}")
	public ResponseEntity<?> getProductItemDetail(@PathVariable int id) {
		try {
			ProductItems productItem = productItemsService.productItemsFindById(id)
					.orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

			ProductItemsDTOS productItemDTO = productItemsMapper.mapper(productItem);

			List<ProductImagesDTOS> images = productImagesService.findByProductItemsId(id).stream()
					.map(productImagesMapper::mapper).collect(Collectors.toList());

			List<PriceHistoriesDTOS> priceHistories = priceHistoriesService.findByProductItemsId(id).stream()
					.map(priceHistoriesMapper::mapper).collect(Collectors.toList());

			List<PromotionsDTOS> promotions = promotionsService.promotionsFindByIsActiveTrueByPromotionItemId(id)
					.stream().map(promotionsMapper::mapper).collect(Collectors.toList());

			ProductItemDetailDTO detail = new ProductItemDetailDTO(productItemDTO, images, priceHistories, promotions);

			return ResponseEntity.ok(detail);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Lỗi: " + e.getMessage()));
		}
	}

	@GetMapping("/ProductItems/detail/test/{id}")
	public ResponseEntity<?> getProductItemsById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(productItemsService.getForeCastDataLast30Days(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Lỗi: " + e.getMessage()));
		}
	}

	@GetMapping("/ProductItems/related/{id}")
	public ResponseEntity<?> getRelatedProductItems(@PathVariable int id) {
		List<ProductItemsDTOS> relatedItems = productItemsService.getRelatedProductItems(id);
		return ResponseEntity.ok(relatedItems);
	}

	@GetMapping("/ProductItems/Discount")
	public ResponseEntity<?> getDiscountProduct() {
		try {
			List<ProductItems> listDiscountProduct = productItemsService.findAllDiscountedProductIds();
			return ResponseEntity.ok(listDiscountProduct);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("MESSAGE", "Đã có lỗi xảy ra"));
		}

	}

}