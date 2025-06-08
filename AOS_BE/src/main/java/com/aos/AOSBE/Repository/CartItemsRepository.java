package com.aos.AOSBE.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.CartItems;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
//	@Query("SELECT a FROM CartItems a WHERE a.accounts.email = ?1and a.productItems.id= ?2 ")
	Optional<CartItems> findByAccountsEmailAndProductItemsId(String email, int productItemsId);

	List<CartItems> findByAccountsEmail(String email);
}
