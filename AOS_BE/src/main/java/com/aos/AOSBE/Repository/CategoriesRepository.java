package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    // Add custom query methods here if needed
}
