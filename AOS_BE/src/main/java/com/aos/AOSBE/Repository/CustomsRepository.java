package com.aos.AOSBE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Customs;

import java.util.List;

@Repository
public interface CustomsRepository extends JpaRepository<Customs, Integer>, JpaSpecificationExecutor<Customs> {
	// Add custom query methods here if needed
    @Query("SELECT c FROM Customs c Where c.account.email = ?1 AND c.orderItem.id IS NULL")
    List<Customs> findCustomRawByEmail(String email);
}
