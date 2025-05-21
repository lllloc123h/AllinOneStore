package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Product_images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_imagesRepository extends JpaRepository<Product_images, Integer> {
    // Add custom query methods here if needed
}
