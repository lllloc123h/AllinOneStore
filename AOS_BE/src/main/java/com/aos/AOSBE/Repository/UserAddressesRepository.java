package com.aos.AOSBE.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.UserAddresses;

@Repository
public interface UserAddressesRepository
		extends JpaRepository<UserAddresses, Integer>, JpaSpecificationExecutor<UserAddresses> {
	Optional<UserAddresses> findByAccountsIdAndIsDefaultTrue(int accountId);

	@Query("SELECT a FROM UserAddresses a  WHERE a.accounts.email = ?1 ")
	List<UserAddresses> findByAccountsEmail(String accountEmail);

	@Query("SELECT a FROM UserAddresses a WHERE a.isDefault = ?1 AND a.accounts.email = ?2")
	Optional<UserAddresses> findBAddressIsDefalut(boolean isDefault, String accountEmail);

}