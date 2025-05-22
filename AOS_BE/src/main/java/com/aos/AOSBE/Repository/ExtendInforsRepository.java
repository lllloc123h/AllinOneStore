package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.ExtendInfors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtendInforsRepository extends JpaRepository<ExtendInfors, String> {
    // Add custom query methods here if needed
}
