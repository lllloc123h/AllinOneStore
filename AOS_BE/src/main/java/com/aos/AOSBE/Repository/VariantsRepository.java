package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Variants;

@Repository
public interface VariantsRepository extends JpaRepository<Variants, Integer>, JpaSpecificationExecutor<Variants> {
	// Add custom query methods here if needed
	@Query("Select v from Variants v where v.name = ?1")
	Optional<Variants> findByName(String name);
}