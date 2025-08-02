package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Entity.UserProductEvents;

@Repository
public interface UserProductEventsRepository
		extends JpaRepository<UserProductEvents, Integer>, JpaSpecificationExecutor<UserProductEvents> {
	// Add custom query methods here if needed
	@Query("SELECT a.productItems FROM UserProductEvents a WHERE a.accounts.gender =  ?1  AND a.eventType= ?2")
	List<ProductItems> findAllBoughtByGender(boolean skuLike, String key);

	@Query("""
			    SELECT a.productItems
			    FROM UserProductEvents a
			    WHERE (FUNCTION('YEAR', a.accounts.birthday) BETWEEN :startYear AND :endYear)
			    AND   a.eventType= :key

			""")
	List<ProductItems> findAllByBirthdayYearRange(@Param("startYear") int start, @Param("endYear") int end,
			@Param("key") String key);

}
