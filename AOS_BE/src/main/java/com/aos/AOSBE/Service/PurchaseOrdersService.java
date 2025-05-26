package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class PurchaseOrdersService {
	@Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public List<PurchaseOrders> purchaseOrdersFindAll() {
        return purchaseOrdersRepository.findAll();
    }
    public PurchaseOrders purchaseOrdersSave(PurchaseOrders purchaseOrders) {
        return purchaseOrdersRepository.save(purchaseOrders);
    }
    public Optional<PurchaseOrders> purchaseOrdersFindById(int id) {
        return purchaseOrdersRepository.findById(id);
    }
    public void purchaseOrdersDeleteById(int id) {
        purchaseOrdersRepository.deleteById(id);
    }
}