package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Integer> {
    // Add custom query methods here if needed
}
