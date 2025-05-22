package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.BillOfMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Integer> {
    // Add custom query methods here if needed
}
