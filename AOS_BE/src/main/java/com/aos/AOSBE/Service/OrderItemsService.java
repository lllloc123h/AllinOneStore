package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class OrderItemsService {
	@Autowired
    private OrderItemsRepository orderItemsRepository;

    public List<OrderItems> orderItemsFindAll() {
        return orderItemsRepository.findAll();
    }
    public OrderItems orderItemsSave(OrderItems orderItems) {
        return orderItemsRepository.save(orderItems);
    }
    public Optional<OrderItems> orderItemsFindById(int id) {
        return orderItemsRepository.findById(id);
    }
    public void orderItemsDeleteById(int id) {
        orderItemsRepository.deleteById(id);
    }
}