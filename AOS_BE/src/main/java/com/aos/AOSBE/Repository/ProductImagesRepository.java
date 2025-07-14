package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ProductImages;

@Repository
public interface ProductImagesRepository
		extends JpaRepository<ProductImages, Integer>, JpaSpecificationExecutor<ProductImages> {
	// Add custom query methods here if needed
	@Query("SELECT a FROM ProductImages a WHERE a.productItems.id =  ?1 ")
	List<ProductImages> findByProductItemsId(int productItemId);
}