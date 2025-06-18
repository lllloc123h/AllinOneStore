package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ProductItems;

@Repository
public interface ProductItemsRepository
		extends JpaRepository<ProductItems, Integer>, JpaSpecificationExecutor<ProductItems> {
	@Query("SELECT a FROM ProductItems a WHERE a.sku like  ?1 ")
	List<ProductItems> findBySkuLike(String skuLike);

	// Add custom query methods here if needed
	@Query("SELECT MIN(p.price),MAX(p.price) FROM ProductItems p WHERE p.baseProducts.id = ?1")
	List<Double> findMinAndMaxPriceByBaseId(int id);

	@Query(value = """
				SELECT  bp.id,
						bp.name,
				        bp.material,
				        bp.category_id,
				        bp.main_image,
				        bp.is_custom,
				        pit.turn_buy,
				        pit.sku,
				        bp.rating,
				        bp.is_active,
				        pit.qty,
				        pit.price
				FROM base_products bp
				JOIN product_items pit ON bp.id = pit.base_id
				WHERE
				    (:colorsIsEmpty = 1 OR EXISTS (
				        SELECT 1 FROM STRING_SPLIT(:colorsJoined, '-') c
				        WHERE pit.sku LIKE '%' + c.value + '%'
				    ))
				AND
				    (:sizesIsEmpty = 1 OR EXISTS (
				        SELECT 1 FROM STRING_SPLIT(:sizesJoined, '-') s
				        WHERE pit.sku LIKE '%' + s.value + '%'
				    ))
				AND
				    (:minPriceIsEmpty = 1 OR EXISTS (
				        SELECT 1 FROM STRING_SPLIT(:minPriceJoined, '-') p
				        WHERE pit.price > TRY_CAST(p.value AS FLOAT)
				    ))
				AND
				    (:maxPriceIsEmpty = 1 OR EXISTS (
				        SELECT 1 FROM STRING_SPLIT(:maxPriceJoined, '-') p
				        WHERE pit.price < TRY_CAST(p.value AS FLOAT)
				    ))
			""", nativeQuery = true)
	List<Object[]> filterItems(@Param("colorsIsEmpty") int colorsIsEmpty, @Param("colorsJoined") String colorsJoined,
			@Param("sizesIsEmpty") int sizesIsEmpty, @Param("sizesJoined") String sizesJoined,
			@Param("minPriceIsEmpty") int minPriceIsEmpty, @Param("minPriceJoined") String minPriceJoined,
			@Param("maxPriceIsEmpty") int maxPriceIsEmpty, @Param("maxPriceJoined") String maxPriceJoined);

	@Query(value = """
				SELECT  bp.id,
						bp.name,
				        bp.material,
				        bp.category_id,
				        bp.main_image,
				        bp.is_custom,
				        pit.turn_buy,
				        pit.sku,
				        bp.rating,
				        bp.is_active,
				        pit.qty,
				        pit.price
				FROM base_products bp
				JOIN product_items pit ON bp.id = pit.base_id
				WHERE
				    (:isSkuLikeListEmpty = 1 OR EXISTS (
				        SELECT 1 FROM STRING_SPLIT(:skuLikeList, ',') c
				        WHERE pit.sku LIKE '%' + c.value + '%'
				    ))
				AND
				    (:minPriceIsEmpty = 1 OR  pit.price >TRY_CAST(:minPrice  AS FLOAT))
				AND

				    (:maxPriceIsEmpty = 1 OR  pit.price <TRY_CAST(:maxPrice  AS FLOAT))
			""", nativeQuery = true)
	Page<Object[]> newFilterItems(Pageable pageable,
//			@Param("colorsIsEmpty") int colorsIsEmpty, @Param("colorsJoined") String colorsJoined,
			@Param("isSkuLikeListEmpty") int isSkuLikeListEmpty, @Param("skuLikeList") String sizesJoined,
			@Param("minPriceIsEmpty") int minPriceIsEmpty, @Param("minPrice") String minPriceJoined,
			@Param("maxPriceIsEmpty") int maxPriceIsEmpty, @Param("maxPrice") String maxPriceJoined);
}