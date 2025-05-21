package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Cancels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelsRepository extends JpaRepository<Cancels, Integer> {
    // Add custom query methods here if needed
}
