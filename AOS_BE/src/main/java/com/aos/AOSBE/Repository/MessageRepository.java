package com.aos.AOSBE.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
	// Add custom query methods here if needed
	@Query("SELECT mbae from Message mbae where mbae.accounts.email= :email ORDER BY mbae.createdAt DESC")
	Page<Message> findByAccountsEmail(@Param("email") String email, Pageable pageable);
}
