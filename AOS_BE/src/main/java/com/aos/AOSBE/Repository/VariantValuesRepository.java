package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.VariantValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantValuesRepository extends JpaRepository<VariantValues, Integer> {
    // Add custom query methods here if needed
}
