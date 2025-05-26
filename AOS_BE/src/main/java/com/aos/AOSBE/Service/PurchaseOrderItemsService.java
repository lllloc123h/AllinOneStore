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
public class PurchaseOrderItemsService {
	@Autowired
    private PurchaseOrderItemsRepository purchaseOrderItemsRepository;

    public List<PurchaseOrderItems> purchaseOrderItemsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return purchaseOrderItemsRepository.findAll(pageable).getContent();
    }
    public PurchaseOrderItems purchaseOrderItemsSave(PurchaseOrderItems purchaseOrderItems) {
        return purchaseOrderItemsRepository.save(purchaseOrderItems);
    }
    public Optional<PurchaseOrderItems> purchaseOrderItemsFindById(int id) {
        return purchaseOrderItemsRepository.findById(id);
    }
    public void purchaseOrderItemsDeleteById(int id) {
        purchaseOrderItemsRepository.deleteById(id);
    }
}