package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ExtendValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtendValuesRepository extends JpaRepository<ExtendValues, Integer> {
    // Add custom query methods here if needed
}
