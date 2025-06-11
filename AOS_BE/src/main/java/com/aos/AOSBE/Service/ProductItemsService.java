package com.aos.AOSBE.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.ProductItemsRepository;

@Service
public class ProductItemsService {
	@Autowired
	private ProductItemsRepository productItemsRepository;

	public List<ProductItems> productItemsFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productItemsRepository.findAll(pageable).getContent();
	}

	public List<ProductItems> productItemsFindAllHaveSkuLike(String skuLike) {

		String skuLikeSplit[] = skuLike.split("-");
		System.out.println(skuLikeSplit);
		String skuLikeConvert = "%" + skuLikeSplit[0] + "%" + skuLikeSplit[1];
		return productItemsRepository.findBySkuLike(skuLikeConvert);
	}

	public List<Object[]> productItemsFilterItemsByColorAndSizePriceMinAndPriceMax(String skuColorLike,
			String skuSizeLike, String minPrice, String maxPrice) {
		int isSkuColorLikeEmpty = (skuColorLike == null || skuColorLike.isEmpty()) ? 0 : 1;
		int isSkuSizeLikeEmpty = (skuSizeLike == null || skuSizeLike.isEmpty()) ? 0 : 1;
		int isMinPriceEmpty = (minPrice == null || minPrice.isEmpty()) ? 0 : 1;
		int isMaxPriceEmpty = (maxPrice == null || maxPrice.isEmpty()) ? 0 : 1;

		skuColorLike = (skuColorLike == null) ? "" : skuColorLike;
		skuSizeLike = (skuSizeLike == null) ? "" : skuSizeLike;
		minPrice = (minPrice == null) ? "" : minPrice;
		maxPrice = (maxPrice == null) ? "" : maxPrice;
		System.out.println(isSkuColorLikeEmpty);
		System.out.println(isSkuSizeLikeEmpty);
		System.out.println(isMinPriceEmpty);
		System.out.println(isMaxPriceEmpty);
		System.out.println(skuColorLike);
		System.out.println(skuSizeLike);
		System.out.println(minPrice);
		System.out.println(maxPrice);
		List<Object[]> objectList = new ArrayList<Object[]>();
		try {
			objectList = productItemsRepository.filterItems(isSkuColorLikeEmpty, skuColorLike, isSkuSizeLikeEmpty,
					skuSizeLike, isMinPriceEmpty, minPrice, isMaxPriceEmpty, maxPrice);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectList;
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
}