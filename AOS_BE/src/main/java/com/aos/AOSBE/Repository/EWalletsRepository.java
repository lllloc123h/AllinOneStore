package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.EWallets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EWalletsRepository extends JpaRepository<EWallets, Integer> {
    // Add custom query methods here if needed
}
