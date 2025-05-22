package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.TraceXeEventMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraceXeEventMapRepository extends JpaRepository<TraceXeEventMap, String> {
    // Add custom query methods here if needed
}
