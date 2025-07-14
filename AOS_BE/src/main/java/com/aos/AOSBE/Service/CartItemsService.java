package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.aos.AOSBE.DTOS.CreateComboDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Repository.ProductItemsRepository;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Repository.PromotionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Repository.CartItemsRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private CartItemsRepository cartItemsRepository;
@Autowired
private ProductItemsRepository productItemsRepository;
@Autowired
private PromotionsRepository promotionsRepository;
@Autowired
private PromotionProductsRepository promotionProductsRepository;
	public Page<CartItems> cartItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CartItems> spec = specBuilder.buildFilter(filters);
		return cartItemsRepository.findAll(spec, pageable);
	}

	public List<CartItems> cartItemsFindAccounts(String email) {
		return cartItemsRepository.findByAccountsEmail(email);
	}
	@Transactional
	public CartItems cartItemsSave(CartItems cartItems) {
		return cartItemsRepository.save(cartItems);
	}

	public Optional<CartItems> cartItemsFindById(int id) {
		return cartItemsRepository.findById(id);
	}
	@Transactional
	public void cartItemsDeleteById(int id) {
		cartItemsRepository.deleteById(id);
	}
	public CartItems cartFindByAccountEmailAndProductItemId(String email, int id) {
		return cartItemsRepository.findByAccountsEmailAndProductItemsId(email, id).orElse(null);
	}
	@Transactional
	public void addCombo(CreateComboDTO entity, Accounts account) {
		cartItemsRepository.deleteById(entity.getCartId());
		String[] comboGroup = new String[entity.getItems().size()];
		for (int i = 0; i < entity.getItems().size(); i++) {
			comboGroup[i] = String.valueOf(entity.getItems().get(i).getItemId());
		}
		String comboGroupString = String.join("-", comboGroup);
		List<CartItems> cartItems = cartItemsRepository.findAllCartItemsByComboGroup(comboGroupString);
		// kiểm tra comboGroup đã tồn tại chưa
		if (cartItems.size() > 0) {
			// kiểm tra số lượng ở các item trong combo
			boolean flag = true;
			for (int i = 0; i < cartItems.size(); i++) {
				int tempQty = cartItems.get(i).getQty()/cartItems.get(i).getComboQty();
				if (tempQty != entity.getItems().get(i).getQuantity()) {
					flag = false;
					break;
				}
			}
			if (flag) {
				// nếu số lượng bằng thì tặng 1 đơn vị
				for (int i = 0; i < cartItems.size(); i++) {
					cartItems.get(i).setComboQty(cartItems.get(i).getComboQty() + 1);
					cartItems.get(i).setQty(cartItems.get(i).getQty()+entity.getItems().get(i).getQuantity());
					cartItemsRepository.save(cartItems.get(i));
				}
			} else {
				// không bằng thì tạo mới combo
				UUID uuid = UUID.randomUUID();
				for (CreateComboDTO.Items item : entity.getItems()){
					CartItems cartItem = new CartItems();
					cartItem.setAccounts(account);
					cartItem.setComboGroup(comboGroupString);
					cartItem.setQty(item.getQuantity());
					cartItem.setComboGroupId(uuid);
					cartItem.setComboQty(1);
					cartItem.setIsGift(promotionProductsRepository.findByProductItems_Id(item.getItemId()).getFirst().isGift());
					cartItem.setProductItems(productItemsRepository.findById(item.getItemId()).orElse(null));
					cartItem.setPromotions(promotionsRepository.findById(item.getPromotionId()).orElse(null));
					cartItemsRepository.save(cartItem);
				}
			}
		} else {
			UUID uuid = UUID.randomUUID();
			for (CreateComboDTO.Items item : entity.getItems()){
				CartItems cartItem = new CartItems();
				cartItem.setAccounts(account);
				cartItem.setComboGroup(comboGroupString);
				cartItem.setQty(item.getQuantity());
				cartItem.setComboGroupId(uuid);
				cartItem.setComboQty(1);
				cartItem.setIsGift(promotionProductsRepository.findByProductItems_Id(item.getItemId()).getFirst().isGift());
				cartItem.setProductItems(productItemsRepository.findById(item.getItemId()).orElse(null));
				cartItem.setPromotions(promotionsRepository.findById(item.getPromotionId()).orElse(null));
				cartItemsRepository.save(cartItem);
			}

		}
	}
	public List<CartItems> findCartItemsByAccountsAndComboGroupId(Accounts accounts, UUID comboGroup) {
		return cartItemsRepository.findCartItemsByAccountsAndComboGroupId(accounts ,comboGroup);
	}
	public void deleteCombo(Accounts account,UUID comboGroupId) {
		List<CartItems> cartItems = cartItemsRepository.findCartItemsByAccountsAndComboGroupId(account,comboGroupId);
		if (cartItems.size() > 0) {
			for (CartItems item : cartItems) {
				cartItemsRepository.delete(item);
			}
		}
	}

}