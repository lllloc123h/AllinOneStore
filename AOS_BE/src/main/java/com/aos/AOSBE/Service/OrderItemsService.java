package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class OrderItemsService {
	@Autowired
    private OrderItemsRepository orderOrderItemsRepository;

    public List<OrderItems> orderOrderItemsFindAll() {
        return orderOrderItemsRepository.findAll();
    }
    public OrderItems orderOrderItemsSave(OrderItems orderOrderItems) {
        return orderOrderItemsRepository.save(orderOrderItems);
    }
    public Optional<OrderItems> orderOrderItemsFindById(int id) {
        return orderOrderItemsRepository.findById(id);
    }
    public void orderOrderItemsDeleteById(int id) {
        orderOrderItemsRepository.deleteById(id);
    }
}