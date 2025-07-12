package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aos.AOSBE.DTOS.CreateComboDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Repository.ProductItemsRepository;
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
	public Page<CartItems> cartItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CartItems> spec = specBuilder.buildFilter(filters);
		return cartItemsRepository.findAll(spec, pageable);
	}

	public List<CartItems> cartItemsFindAccounts(String email) {
		return cartItemsRepository.findByAccountsEmail(email);
	}

	public CartItems cartItemsSave(CartItems cartItems) {
		return cartItemsRepository.save(cartItems);
	}

	public Optional<CartItems> cartItemsFindById(int id) {
		return cartItemsRepository.findById(id);
	}

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
		if (cartItems.size() > 0) {
			for (CartItems cartItem : cartItems) {
				cartItem.setQty(cartItem.getQty() + cartItem.getComboQty());
				cartItem.setComboQty(cartItem.getComboQty() + 1);
				cartItemsRepository.save(cartItem);
			}
		} else {
			for (CreateComboDTO.Items item : entity.getItems()){
				CartItems cartItem = new CartItems();
				cartItem.setAccounts(account);
				cartItem.setComboGroup(comboGroupString);
				cartItem.setQty(item.getQuantity());
				cartItem.setComboQty(1);
				cartItem.setProductItems(productItemsRepository.findById(item.getItemId()).orElse(null));
				cartItem.setPromotions(promotionsRepository.findById(item.getPromotionId()).orElse(null));
				cartItemsRepository.save(cartItem);
			}

		}
	}
}