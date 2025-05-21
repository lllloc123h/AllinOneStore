package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Trace_xe_action_map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Trace_xe_action_mapRepository extends JpaRepository<Trace_xe_action_map, String> {
    // Add custom query methods here if needed
}
