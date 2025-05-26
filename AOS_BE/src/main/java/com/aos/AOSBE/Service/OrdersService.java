package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class OrdersService {
	@Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> ordersFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return ordersRepository.findAll(pageable).getContent();
    }
    public Orders ordersSave(Orders orders) {
        return ordersRepository.save(orders);
    }
    public Optional<Orders> ordersFindById(int id) {
        return ordersRepository.findById(id);
    }
    public void ordersDeleteById(int id) {
        ordersRepository.deleteById(id);
    }
}