package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Variant_values;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Variant_valuesRepository extends JpaRepository<Variant_values, Integer> {
    // Add custom query methods here if needed
}
