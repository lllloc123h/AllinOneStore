package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Sysdiagrams;

@Repository
public interface SysdiagramsRepository
		extends JpaRepository<Sysdiagrams, String>, JpaSpecificationExecutor<Sysdiagrams> {
	// Add custom query methods here if needed
}