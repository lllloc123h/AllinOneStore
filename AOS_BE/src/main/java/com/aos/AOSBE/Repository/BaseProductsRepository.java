package com.aos.AOSBE.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.BaseProducts;

@Repository
public interface BaseProductsRepository extends JpaRepository<BaseProducts, Integer> {
	// Add custom query methods here if needed

//    SELECT bp.id,bp.name,bp.material,bp.main_image,bp.is_active,bp.is_custom,bp.turn_buy,bp.rating
//    from base_products bp
//    inner join product_items pit on bp.id = pit.base_id
//    Where pit.sku LIKE '%DEN%' and pit.price between  and
//    @Query("SELECT b from b.id," +
//            "b.name," +
//            "b.material," +
//            "b.mainImage," +
//            "b.isActive," +
//            "b.isCustom,b.turnBuy," +
//            "b.rating from BaseProducts b inner join ProductItems pit on b.id = pit.baseProducts.id" +
//            " Where and b.categories.name LIKE '%?1%' AND  b.name LIKE '%?2%' AND pit.sku LIKE '%?3%' and pit.price between ?4 and ?5")
//    Page<BaseProductsDTOS> findBaseProductsByCategoryAndNameAndSkuAndPrice(Pageable pageable, String categories, String name, String sku, Double minPrice, Double maxPrice);

	@Query(value = """
			SELECT
				bp.id,
				bp.name,
				bp.material,
				bp.main_image,
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
					bp.main_image,
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
