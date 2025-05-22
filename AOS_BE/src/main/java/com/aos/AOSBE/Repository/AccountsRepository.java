package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
    // Add custom query methods here if needed
}
