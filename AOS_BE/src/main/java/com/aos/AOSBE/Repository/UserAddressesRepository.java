package com.aos.AOSBE.Repository;

import com.aos.AOSBE.Entity.UserAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressesRepository extends JpaRepository<UserAddresses, Integer> {
    // Add custom query methods here if needed
}
