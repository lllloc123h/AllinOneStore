package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.UserLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogsRepository extends JpaRepository<UserLogs, Integer> {
    // Add custom query methods here if needed
}
