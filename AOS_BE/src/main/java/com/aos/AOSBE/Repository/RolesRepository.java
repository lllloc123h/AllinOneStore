package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>, JpaSpecificationExecutor<Roles> {
	// Add custom query methods here if needed
	// tìm role theo name
	@Query("select r from Roles r where r.name=?1")
	Optional<Roles> findByName(String name);

//	@Query("select r from Roles r where r.")
//	Optional<Roles> findByAccountEmail(String email);
}