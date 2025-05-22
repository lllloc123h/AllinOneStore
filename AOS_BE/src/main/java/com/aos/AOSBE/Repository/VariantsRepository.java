package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Variants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantsRepository extends JpaRepository<Variants, Integer> {
    // Add custom query methods here if needed
}
