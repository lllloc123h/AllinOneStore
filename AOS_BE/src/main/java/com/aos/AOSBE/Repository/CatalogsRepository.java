package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Catalogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogsRepository extends JpaRepository<Catalogs, Integer> {
    // Add custom query methods here if needed
}
