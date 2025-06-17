package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ProductImages;

@Repository
public interface ProductImagesRepository
		extends JpaRepository<ProductImages, Integer>, JpaSpecificationExecutor<ProductImages> {
	// Add custom query methods here if needed
}