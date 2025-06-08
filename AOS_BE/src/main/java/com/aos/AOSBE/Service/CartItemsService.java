package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Repository.CartItemsRepository;

@Service
public class CartItemsService {
	@Autowired
	private CartItemsRepository cartItemsRepository;

	public List<CartItems> cartItemsFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return cartItemsRepository.findAll(pageable).getContent();
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
}