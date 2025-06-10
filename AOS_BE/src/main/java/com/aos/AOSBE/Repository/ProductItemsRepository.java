package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ProductItems;

@Repository
public interface ProductItemsRepository extends JpaRepository<ProductItems, Integer> {
	@Query("SELECT a FROM ProductItems a WHERE a.sku like  ?1 ")
	List<ProductItems> findBySkuLike(String skuLike);
}
