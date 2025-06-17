package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.UserAddresses;

@Repository
public interface UserAddressesRepository
		extends JpaRepository<UserAddresses, Integer>, JpaSpecificationExecutor<UserAddresses> {
	Optional<UserAddresses> findByAccountsIdAndIsDefaultTrue(int accountId);
}