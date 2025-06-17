package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.UserLogs;

@Repository
public interface UserLogsRepository extends JpaRepository<UserLogs, Integer>, JpaSpecificationExecutor<UserLogs> {
	// Add custom query methods here if needed
}