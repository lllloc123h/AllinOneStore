package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.UserInfors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInforsRepository extends JpaRepository<UserInfors, String> {
    // Add custom query methods here if needed
}
