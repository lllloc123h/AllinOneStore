package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
    // Add custom query methods here if needed
}
