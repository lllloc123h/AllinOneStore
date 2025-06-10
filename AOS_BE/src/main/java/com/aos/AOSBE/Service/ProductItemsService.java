package com.aos.AOSBE.Service;

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

	public List<ProductItems> productItemsFilterItemsByColorAndSize(String skuColorLike, String skuSizeLike) {
		int isSkuColorLikeEmty = skuColorLike.isEmpty() ? 1 : 0;
		int isSkuSizeLikeEmty = skuSizeLike.isEmpty() ? 1 : 0;
		return productItemsRepository.filterItemsByColorAndSize(isSkuColorLikeEmty, skuColorLike, isSkuSizeLikeEmty,
				skuSizeLike);
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