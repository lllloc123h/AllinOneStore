package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.User_addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_addressesRepository extends JpaRepository<User_addresses, Integer> {
    // Add custom query methods here if needed
}
