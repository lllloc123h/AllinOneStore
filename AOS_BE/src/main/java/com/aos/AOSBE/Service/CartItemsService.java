package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class CartItemsService {
	@Autowired
    private CartItemsRepository cartItemsRepository;

    public List<CartItems> cartItemsFindAll() {
        return cartItemsRepository.findAll();
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
}