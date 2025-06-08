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
import org.springframework.transaction.annotation.Transactional;
@Service
public class PurchaseOrdersService {
	@Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public List<PurchaseOrders> purchaseOrdersFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return purchaseOrdersRepository.findAll(pageable).getContent();
    }
    @Transactional
    public PurchaseOrders purchaseOrdersSave(PurchaseOrders purchaseOrders) {
        return purchaseOrdersRepository.save(purchaseOrders);
    }
    public Optional<PurchaseOrders> purchaseOrdersFindById(int id) {
        return purchaseOrdersRepository.findById(id);
    }
    @Transactional
    public void purchaseOrdersDeleteById(int id) {
        purchaseOrdersRepository.deleteById(id);
    }
}