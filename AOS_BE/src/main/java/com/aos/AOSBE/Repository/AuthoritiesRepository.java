package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Authorities;

@Repository
public interface AuthoritiesRepository
		extends JpaRepository<Authorities, Integer>, JpaSpecificationExecutor<Authorities> {
	// Add custom query methods here if needed
	@Query("SELECT a FROM Authorities a WHERE a.accounts.email = ?1")
	List<Authorities> findAllByEmail(String email);

	@Query("SELECT a FROM Authorities a WHERE a.accounts.id = ?1")
	List<Authorities> findAllByAccountsId(int accountId);

	@Query("SELECT a.roles.name FROM Authorities a WHERE a.accounts.email = ?1")
	List<String> findAllRoleByAccountEmail(String email);

}