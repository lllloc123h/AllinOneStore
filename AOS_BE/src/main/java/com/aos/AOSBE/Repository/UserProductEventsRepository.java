package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.UserProductEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface UserProductEventsRepository extends JpaRepository<UserProductEvents, Integer>, JpaSpecificationExecutor<UserProductEvents> {
    // Add custom query methods here if needed
}
