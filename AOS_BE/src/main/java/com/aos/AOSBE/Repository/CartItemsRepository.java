package com.aos.AOSBE.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.CartItems;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer>, JpaSpecificationExecutor<CartItems> {
	@Query("SELECT a FROM CartItems a WHERE a.accounts.email = ?1  and a.productItems.id= ?2 ")
	Optional<CartItems> findByAccountsEmailAndProductItemsId(String email, int productItemsId);

	List<CartItems> findByAccountsEmail(String email);

	List<CartItems> findAllCartItemsByComboGroup(String comboGroup);

	List<CartItems> findCartItemsByAccountsAndComboGroupId(Accounts accounts, UUID comboGroupId);
}