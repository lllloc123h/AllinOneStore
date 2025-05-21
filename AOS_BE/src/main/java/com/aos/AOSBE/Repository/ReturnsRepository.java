package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Integer> {
    // Add custom query methods here if needed
}
