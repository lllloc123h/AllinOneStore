package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class CartItemsService {
	@Autowired
    private CartItemsRepository cartCartItemsRepository;

    public List<CartItems> cartCartItemsFindAll() {
        return cartCartItemsRepository.findAll();
    }
    public CartItems cartCartItemsSave(CartItems cartCartItems) {
        return cartCartItemsRepository.save(cartCartItems);
    }
    public Optional<CartItems> cartCartItemsFindById(int id) {
        return cartCartItemsRepository.findById(id);
    }
    public void cartCartItemsDeleteById(int id) {
        cartCartItemsRepository.deleteById(id);
    }
}