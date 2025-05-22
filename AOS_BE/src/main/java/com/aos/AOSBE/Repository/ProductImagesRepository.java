package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {
    // Add custom query methods here if needed
}
