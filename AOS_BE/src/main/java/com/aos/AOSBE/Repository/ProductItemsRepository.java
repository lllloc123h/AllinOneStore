package com.aos.AOSBE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.ProductItems;

@Repository
public interface ProductItemsRepository extends JpaRepository<ProductItems, Integer> {
	@Query("SELECT a FROM ProductItems a WHERE a.sku like  ?1 ")
	List<ProductItems> findBySkuLike(String skuLike);

	// Add custom query methods here if needed
	@Query("SELECT MIN(p.price),MAX(p.price) FROM ProductItems p WHERE p.baseProducts.id = ?1")
	List<Double> findMinAndMaxPriceByBaseId(int id);

	@Query(value = """
			SELECT * FROM product_items
			WHERE
			    (:colorsIsEmpty = 1 OR EXISTS (
			        SELECT 1 FROM STRING_SPLIT(:colorsJoined, '-') c
			        WHERE sku LIKE '%' + c.value + '%'
			    ))
			AND
			    (:sizesIsEmpty = 1 OR EXISTS (
			        SELECT 1 FROM STRING_SPLIT(:sizesJoined, '-') s
			        WHERE sku LIKE '%' + s.value + '%'
			    ))
			""", nativeQuery = true)
	List<ProductItems> filterItemsByColorAndSize(@Param("colorsIsEmpty") int colorsIsEmpty,
			@Param("colorsJoined") String colorsJoined, @Param("sizesIsEmpty") int sizesIsEmpty,
			@Param("sizesJoined") String sizesJoined);

}
