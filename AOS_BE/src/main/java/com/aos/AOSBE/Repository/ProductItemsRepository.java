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

	@Query("SELECT a FROM ProductItems a WHERE a.baseProducts.id =  ?1")
	List<ProductItems> findByBaseProductsId(int id);
	@Query("SELECT a FROM ProductItems a WHERE a.baseProducts.id =  ?1 " )
	List<ProductItems> findByBaseProductsIdAndIsActive(int id, boolean isActive);


	@Query("SELECT a FROM ProductItems a WHERE a.baseProducts.id =  ?1   AND a.isActive = true")
	List<ProductItems> findByBaseProductsIdWithTheActiveTrue(int id);

	// Add custom query methods here if needed
	@Query("SELECT MIN(p.price),MAX(p.price) FROM ProductItems p WHERE p.baseProducts.id = ?1")
	List<Double> findMinAndMaxPriceByBaseId(int id);

	@Query(value = """
				SELECT  bp.id,
						bp.name,
				        bp.material,
				        bp.category_id,
				        bp.main_image_url,
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
				SELECT
				  		bp.id,
						bp.name,
				        bp.material,
				        bp.category_id,
				        bp.main_image_url,
				        bp.is_custom,
				        bp.turn_buy,
				        bp.rating,
				        bp.is_active,
						SUM(pit.qty) AS qty,
						STRING_AGG(CAST(pit.price AS VARCHAR), ', ') AS list_price_raw
				FROM base_products bp
				JOIN product_items pit ON bp.id = pit.base_id
				JOIN Categories cate ON bp.category_id=cate.id
				WHERE
				    (:isSkuLikeListEmpty = 1
				    OR
					EXISTS (
				        SELECT 1 FROM STRING_SPLIT(:skuLikeList, ',') c
				        WHERE pit.sku LIKE '%' + c.value + '%'
				    )
					)
				AND
					(:isKeyWordEmpty = 1
				     OR
				      bp.name like '%' + :keyWord + '%'
				   	)
				AND
					(:isCategoriesEmpty = 1
						OR
					EXISTS (
				        SELECT 1
				        	FROM STRING_SPLIT(:categoriesList, '-') c
				        	WHERE cate.name = c.value
				    	)
				    )
				AND
				    (:minPriceIsEmpty = 1 OR  pit.price >TRY_CAST(:minPrice  AS FLOAT))
				AND
				    (:maxPriceIsEmpty = 1 OR  pit.price <TRY_CAST(:maxPrice  AS FLOAT))
				AND
				    (:idProductItemIsEmpty = 1 OR  pit.id = :idProductItem)
				AND
					bp.is_active = 1
				AND
					pit.is_active = 1
				AND
					qty > 0
				GROUP BY
						bp.id,
						bp.name,
				        bp.material,
				        bp.category_id,
				        bp.main_image_url,
				        bp.is_custom,
				        bp.turn_buy,
				        bp.rating,
				        bp.is_active
				Order by bp.id ASC

			""", nativeQuery = true)
	Page<Object[]> newFilterItems(Pageable pageable, @Param("isSkuLikeListEmpty") int isSkuLikeListEmpty,
			@Param("skuLikeList") String sizesJoined, @Param("minPriceIsEmpty") int minPriceIsEmpty,
			@Param("minPrice") String minPriceJoined, @Param("maxPriceIsEmpty") int maxPriceIsEmpty,
			@Param("maxPrice") String maxPriceJoined, @Param("isCategoriesEmpty") int isCategoriesEmpty,
			@Param("categoriesList") String categoriesList, @Param("isKeyWordEmpty") int isKeyWordEmpty,
			@Param("keyWord") String keyWord, @Param("idProductItemIsEmpty") int idProductItemIsEmpty,
			@Param("idProductItem") int idProductItem);

	@Query(value = """
			    SELECT
			        pi.id,
			        bp.name,
			        pi.price,
			        promo.discount_type,
			        promo.name,
			        promo.start_at,
			        promo.end_at,
			        img.image_url
			    FROM product_items pi
			    JOIN base_products bp ON pi.base_id = bp.id
			    JOIN promotion_products pp ON pi.id = pp.product_item_id
			    JOIN promotions promo ON pp.promotion_id = promo.id
			    LEFT JOIN product_images img ON img.product_item_id = pi.id
			    WHERE promo.is_active = 1
			      AND promo.type = 'DISCOUNT'
			      AND CURRENT_TIMESTAMP BETWEEN promo.start_at AND promo.end_at
			""", nativeQuery = true)
	List<Object[]> getAllDiscountedProducts();

	List<ProductItems> findTop6ByBaseProducts_Categories_IdAndIdNot(Long categoryId, Long id);

	@Query("""
			    SELECT pi FROM ProductItems pi
			    WHERE pi.baseProducts.categories.id = ?1
			      AND pi.id <> ?2
			""")
	Page<ProductItems> findRelatedItems(Long categoryId, Long productId, Pageable pageable);

	@Query("SELECT p.productItems FROM PromotionProducts p")
	List<ProductItems> findAllDiscountedProductIds();

	@Query("""
			SELECT pi FROM ProductItems pi
			JOIN pi.baseProducts bp
			WHERE bp.isActive = true
			AND pi.qty > 0
			ORDER BY bp.turnBuy DESC
			""")
	List<ProductItems> findBestSellersWithPrice(Pageable pageable);
}