package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer>, JpaSpecificationExecutor<Accounts> {
	// Add custom query methods here if needed
	Optional<Accounts> findByEmail(String email);

	@Query("Select acToCount FROM Accounts acToCount where acToCount.fullname = ?1 ")
	Optional<Accounts> findAccountToCountEvents(String name);
}