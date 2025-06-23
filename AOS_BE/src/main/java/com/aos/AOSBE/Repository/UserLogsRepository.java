package com.aos.AOSBE.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.UserLogs;

@Repository
public interface UserLogsRepository extends JpaRepository<UserLogs, Integer>, JpaSpecificationExecutor<UserLogs> {

    @Query("SELECT DISTINCT u.userId FROM UserLogs u WHERE u.createdAt > :cutoff")
    List<Integer> findDistinctUserIdAfter(@Param("cutoff") LocalDateTime cutoff);

}
