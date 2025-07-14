package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Catalogs;

@Repository
public interface CatalogsRepository extends JpaRepository<Catalogs, Integer>, JpaSpecificationExecutor<Catalogs> {
	// Add custom query methods here if needed
	@Query(" select ct from Catalogs ct where ct.name = ?1")
	Optional<Catalogs> findByCatalogName(String name);
}