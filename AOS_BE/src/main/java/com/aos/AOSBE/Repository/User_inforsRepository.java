package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.User_infors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_inforsRepository extends JpaRepository<User_infors, String> {
    // Add custom query methods here if needed
}
