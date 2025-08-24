package com.aos.AOSBE.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.CartItems;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer>, JpaSpecificationExecutor<CartItems> {

    // Cũ: không còn dùng
//	@Query("SELECT a FROM CartItems a WHERE a.accounts.email = ?1  and a.productItems.id= ?2 ")
//	Optional<CartItems> findByAccountsEmailAndProductItemsId(String email, int productItemsId);

    List<CartItems> findByAccountsEmail(String email);

    List<CartItems> findAllCartItemsByComboGroup(String comboGroup);

    List<CartItems> findCartItemsByAccountsAndComboGroupId(Accounts accounts, UUID comboGroupId);

    @Modifying
    @Query("UPDATE CartItems c SET c.promotions.id = NULL WHERE c.promotions.id = ?1")
    void updateCartItemsWherePromotionIsNotExist(int promotionId);

    // ✅ Mới: xử lý chính xác từng trường hợp
    @Query("SELECT a FROM CartItems a WHERE a.accounts.email = ?1 AND a.productItems.id = ?2 AND a.comboGroupId = ?3")
    Optional<CartItems> findByAccountsEmailAndProductItemsIdAndComboGroupId(String email, int productItemsId, UUID comboGroupId);

    @Query("SELECT a FROM CartItems a WHERE a.accounts.email = ?1 AND a.productItems.id = ?2 AND a.comboGroupId IS NULL")
    Optional<CartItems> findByAccountsEmailAndProductItemsIdAndNoCombo(String email, int productItemsId);
}
