package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PurchaseOrderItems;

@Repository
public interface PurchaseOrderItemsRepository
		extends JpaRepository<PurchaseOrderItems, Integer>, JpaSpecificationExecutor<PurchaseOrderItems> {
	// Add custom query methods here if needed
}