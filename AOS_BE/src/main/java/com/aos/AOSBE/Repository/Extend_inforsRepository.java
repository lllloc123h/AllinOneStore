package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Extend_infors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Extend_inforsRepository extends JpaRepository<Extend_infors, String> {
    // Add custom query methods here if needed
}
