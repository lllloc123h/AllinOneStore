package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.VariantValues;

import java.util.List;

@Repository
public interface VariantValuesRepository
		extends JpaRepository<VariantValues, Integer>, JpaSpecificationExecutor<VariantValues> {
	@Query(" select VarVal from VariantValues VarVal where VarVal.signalSku= ?1")
	VariantValues findBySignalSku(String signalSku);
	@Query(" select VarVal from VariantValues VarVal where VarVal.variants.name=?1")
	List<VariantValues> findByNameVariant(String description);
}