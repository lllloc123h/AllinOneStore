package com.aos.AOSBE.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
	// Add custom query methods here if needed
	@Query("SELECT mbae from Message where mbae.accounts.emial=?1")
	Page<Message> findByAccountEmail(String email, Pageable pageable);
}
