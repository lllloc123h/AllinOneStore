package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Catalogs;

@Repository
public interface CatalogsRepository extends JpaRepository<Catalogs, Integer>, JpaSpecificationExecutor<Catalogs> {
	// Add custom query methods here if needed
}