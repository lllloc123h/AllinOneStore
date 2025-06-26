package com.aos.AOSBE.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.EWallets;

@Repository
public interface EWalletsRepository extends JpaRepository<EWallets, Integer>, JpaSpecificationExecutor<EWallets> {

	@Query("SELECT a FROM EWallets a  WHERE a.accounts.email = ?1 ")
	Optional<EWallets> findByAccountEmail(String userEmail);

}