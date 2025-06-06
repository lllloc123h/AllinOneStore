package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
    // Add custom query methods here if needed
	Optional<Accounts> findByEmail(String email);
}
