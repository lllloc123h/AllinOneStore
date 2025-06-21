package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
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
}