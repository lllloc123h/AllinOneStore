package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.EmailTEGV;

@Repository
public interface EmailTEGVRepository extends JpaRepository<EmailTEGV, Integer>, JpaSpecificationExecutor<EmailTEGV> {
	// Add custom query methods here if needed
	List<EmailTEGV> findByNameTEGV(String nameTEGV);
}
