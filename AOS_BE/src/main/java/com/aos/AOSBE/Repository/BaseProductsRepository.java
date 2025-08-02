package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.BaseProducts;

@Repository
public interface BaseProductsRepository
		extends JpaRepository<BaseProducts, Integer>, JpaSpecificationExecutor<BaseProducts> {
	// Add custom query methods here if needed
	@Query("SELECT b FROM BaseProducts b WHERE b.isActive = true ORDER BY b.turnBuy DESC")
	List<BaseProducts> findBestSellers(Pageable pageable);

	@Query(value = """
			SELECT
				bp.id,
				bp.name,
				bp.material,
				bp.main_image_url,
				bp.category_id,
				bp.is_custom,
				bp.turn_buy,
				bp.rating,
				pit.description,
				bp.is_active,
				pit.safety_stock,
				SUM(pit.qty) AS qty,
				STRING_AGG(CAST(pit.price AS VARCHAR), ', ') AS list_price_raw
			FROM base_products bp
			JOIN product_items pit
				ON bp.id = pit.base_id
			JOIN categories cg
				ON  bp.category_id = cg.id
			JOIN catalogs cl
				ON  cg.catalog_id = cl.id
			WHERE
				(:searchByCategoryId = -1 OR bp.category_id = :searchByCategoryId)
				AND
				(:searchByKeyWord IS NULL OR :searchByKeyWord = '' OR LOWER(bp.name) LIKE LOWER(CONCAT('%', :searchByKeyWord, '%')))
			GROUP BY bp.id,
					 bp.name,
					 bp.material,
					bp.main_image_url,
					bp.category_id,
					bp.is_custom,
					bp.turn_buy,
					bp.rating,
					pit.description,
					bp.is_active,
					pit.safety_stock
							""", nativeQuery = true)
	Page<Object[]> findAllWithListPriceRaw(Pageable pageable, @Param("searchByKeyWord") String searchByKeyWord,
			@Param("searchByCategoryId") int searchByCategoryId);

}