package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Integer> {
    // Add custom query methods here if needed
}
