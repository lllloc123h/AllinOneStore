package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Components;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentsRepository extends JpaRepository<Components, Integer> {
    // Add custom query methods here if needed
}
