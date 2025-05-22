package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Sysdiagrams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysdiagramsRepository extends JpaRepository<Sysdiagrams, String> {
    // Add custom query methods here if needed
}
