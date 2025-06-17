package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.PurchaseOrders;

@Repository
public interface PurchaseOrdersRepository
		extends JpaRepository<PurchaseOrders, Integer>, JpaSpecificationExecutor<PurchaseOrders> {
	// Add custom query methods here if needed
}