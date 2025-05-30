package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.TraceXeActionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraceXeActionMapRepository extends JpaRepository<TraceXeActionMap, String> {
    // Add custom query methods here if needed
}
