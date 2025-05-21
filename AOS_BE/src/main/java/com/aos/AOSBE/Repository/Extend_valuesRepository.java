package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Extend_values;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Extend_valuesRepository extends JpaRepository<Extend_values, Integer> {
    // Add custom query methods here if needed
}
