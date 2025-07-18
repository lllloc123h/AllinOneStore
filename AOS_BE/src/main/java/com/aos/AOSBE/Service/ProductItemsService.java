package com.aos.AOSBE.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.aos.AOSBE.DTOS.DiscountedProductDTOS;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Mapper.ProductItemsMapper;
import com.aos.AOSBE.Repository.ProductItemsRepository;

@Service
public class ProductItemsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;

	public List<DiscountedProductDTOS> getDiscountedProducts() {

		List<Object[]> rows = productItemsRepository.getAllDiscountedProducts();

		return rows.stream().map(r -> new DiscountedProductDTOS((Integer) r[0], // productItemId
				(String) r[1], // baseProductName
				new BigDecimal(r[2].toString()), // price as BigDecimal
				(String) r[3], // discountType
				(String) r[4], // promotionName
				((Timestamp) r[5]).toLocalDateTime(), // startAt
				((Timestamp) r[6]).toLocalDateTime(), // endAt
				(String) r[7] // imageUrl
		)).toList();
	}

	@Autowired
	private ProductItemsRepository productItemsRepository;

	@Autowired
	private ProductItemsMapper productItemsMapper;

	public Page<ProductItems> productItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ProductItems> spec = specBuilder.buildFilter(filters);
		return productItemsRepository.findAll(spec, pageable);
	}

	public Page<ProductItems> productItemsFindByBaseProductId(int page, int size, int id) {
		Pageable pageable = PageRequest.of(page, size);
		// Specification<ProductItems> spec = specBuilder.buildFilter(filters);
		return productItemsRepository.findByBaseProductsId(pageable, id);
	}

	public List<ProductItems> productItemsFindAllHaveSkuLike(String skuLike) {
		String skuLikeSplit[] = skuLike.split("-");
		System.out.println(skuLikeSplit);
		String skuLikeConvert = "%" + skuLikeSplit[0] + "%" + skuLikeSplit[1];
		return productItemsRepository.findBySkuLike(skuLikeConvert);
	}

	public Page<Object[]> productItemsFilterItemsByColorAndSizePriceMinAndPriceMax(int page, int size,
			String skuColorLike, String skuSizeLike, String minPrice, String maxPrice, Integer idProductItem) {
		Pageable pageable = PageRequest.of(page, size);
		int isMinPriceEmpty = (minPrice == null || minPrice.isEmpty()) ? 1 : 0;
		int isMaxPriceEmpty = (maxPrice == null || maxPrice.isEmpty()) ? 1 : 0;
		int idProductItemIsEmpty = (idProductItem == null) ? 1 : 0;

		skuColorLike = (skuColorLike == null) ? "" : skuColorLike;
		skuSizeLike = (skuSizeLike == null) ? "" : skuSizeLike;
		minPrice = (minPrice == null) ? "" : minPrice;
		maxPrice = (maxPrice == null) ? "" : maxPrice;
		HandleListSkuToFilter buildKey = new HandleListSkuToFilter();
		String skuList = buildKey.buildKeyFilter(skuColorLike, skuSizeLike);
		int isSkuLikeListEmpty = (skuList == null || skuColorLike.isEmpty()) ? 1 : 0;
		System.out.println(skuList);
		try {
			return productItemsRepository.newFilterItems(pageable, isSkuLikeListEmpty, skuList, isMinPriceEmpty,
					minPrice, isMaxPriceEmpty, maxPrice, idProductItemIsEmpty,
					idProductItem != null ? idProductItem.intValue() : 0);
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
}
