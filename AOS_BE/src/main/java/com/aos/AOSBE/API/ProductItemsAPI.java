package com.aos.AOSBE.API;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Entity.ProductImages;
import com.aos.AOSBE.Mapper.*;
import com.aos.AOSBE.Service.*;
import org.springframework.ai.document.Document;
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

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.Entity.ProductItems;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductItemsAPI {
	@Autowired
	private ProductItemsService productItemsService;

	@Autowired
	private HandleListSkuToFilter handleListSkuToFilter;
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
	private ProductImagesService imagesService;

	@Autowired
	private PromotionProductsService promotionProductsService;
	@Autowired
	private PromotionProductFillterMapper promotionProductFillterMapper;
	@Autowired
	private ReviewsService reviewsService;
	@Autowired
	private OrderItemsService orderItemsService;
	@Autowired
	private ReturnsService returnsService;
	@Autowired
	private QdrantService qdrantService;
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
			List<ProductItems> productItems = productItemsService.productItemsFindByBaseProductId(id);
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
	@GetMapping("/admin/Stats/BaseProducts/ProductItems")
public ResponseEntity<?> getProductItemsStatsByBaseProducts(@RequestParam("baseId") int baseId) {
		try {
			List<ProductItems> productItems = productItemsService.productItemsFindByBaseProductId(baseId);
			List<ProductItemStatsDTO> response = new ArrayList<>();
			for (ProductItems item : productItems) {
				Double damge = orderItemsService.costProductItemByStatus("damage","%%", item.getId());
				Double lost =orderItemsService.costProductItemByStatus("lost","%%", item.getId());
				Double exception = orderItemsService.costProductItemByStatus("exception","%%", item.getId());
				response.add(
						new ProductItemStatsDTO(
						productItemsMapper.mapper(item),
						reviewsService.getAverageRatingByProductItemId(item.getId()),
								orderItemsService.revenueProductItemByStatus("delivered","paid", item.getId()),
								orderItemsService.discountProductItemByStatus("delivered","paid", item.getId()),
								orderItemsService.costProductItemByStatus("delivered","paid", item.getId()),
								orderItemsService.countProductItemByStatus("returned","%%", item.getId()),
								(damge != null ? damge : 0) + (lost != null ? lost : 0) + (exception != null ? exception : 0)
					)
				);
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("Message", "Đã có lỗi xảy ra" + e.getMessage()));
		}
	}

	@PostMapping("/admin/ProductItems")
	public ResponseEntity<?> addNewProductItems(@RequestBody ProductItemsDTOS entity) {
		try {
			ProductItems mapped = productItemsMapper.mapperToObject(entity);
			mapped.setId(null);
			mapped.setSearchPoint(UUID.randomUUID());
			ProductItems saved = productItemsService.productItemsSave(mapped);
			ProductImages images = new ProductImages();
			images.setProductItems(saved);
			images.setImageUrl(entity.getImageUrl());
			images.setDefault(true);
			productImagesService.productImagesSave(images);
			List<Document> docs =qdrantService.createDocumentForChatBotSearch(saved);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}
	}

	@PutMapping("/admin/ProductItems/{id}")
	public ResponseEntity<?> updateProductItems(@PathVariable int id, @RequestBody ProductItemsDTOS entity) {
		try {
			ProductItems mapped = productItemsMapper.mapperToObjectUpdateMethod(entity);
			ProductItems updated = productItemsService.productItemsSave(mapped);
			return ResponseEntity.ok(updated);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}
	}

	@DeleteMapping("/admin/ProductItems/{id}")
	public ResponseEntity<?> deleteProductItems(@PathVariable int id) {
	try {
			productItemsService.productItemsDeleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: " + e.getMessage()));
		}
	}

	// PromotionProductController.java
	@GetMapping("/discounted-products")
	public ResponseEntity<List<DiscountedProductDTOS>> getDiscountedProducts() {
		LocalDateTime now = LocalDateTime.now();
		List<Map<String, Object>> rawResults = promotionProductsService.findDiscountedProductsNative();

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
				item.setName((String) e[1]);
				item.setMaterial((String) e[2]);
				item.setCategoryId((int) e[3]);
				item.setMainImage((String) e[4]);
				item.setCustom((boolean) e[5]);
				item.setTurnBuy((int) e[6]);
				item.setRating((int) e[7]);
				item.setActive((boolean) e[8]);
				item.setQty((int) e[9]);
				item.setListPrice((String) e[10]);
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

			List<ProductItems> productItem = productItemsService.productItemsFindByBaseProductIdTheActiveTrue(id);

			List<ProductItemsDTOS> content = new ArrayList<>();
			for (ProductItems item : productItem) {
				content.add(productItemsMapper.mapperObjectForProductDetail(item));
			}
			Map<String, Object> response = new HashMap();
			response.put("content", content);
			return ResponseEntity.ok(response);

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
	@GetMapping("/admin/products/productitems")
	public ResponseEntity<?> getAllProductItems(@RequestParam("baseId") int baseId) {
			List<PromotionProductFillterDTO> productItems = productItemsService.productItemsByActiveIsTrue(baseId)
				.stream().map(promotionProductFillterMapper::mapper).collect(Collectors.toList());
		return ResponseEntity.ok(productItems);
	}

}