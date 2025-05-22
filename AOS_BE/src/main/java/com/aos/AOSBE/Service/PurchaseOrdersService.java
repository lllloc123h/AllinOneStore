package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class PurchaseOrdersService {
	@Autowired
    private PurchaseOrdersRepository purchasePurchaseOrdersRepository;

    public List<PurchaseOrders> purchasePurchaseOrdersFindAll() {
        return purchasePurchaseOrdersRepository.findAll();
    }
    public PurchaseOrders purchasePurchaseOrdersSave(PurchaseOrders purchasePurchaseOrders) {
        return purchasePurchaseOrdersRepository.save(purchasePurchaseOrders);
    }
    public Optional<PurchaseOrders> purchasePurchaseOrdersFindById(int id) {
        return purchasePurchaseOrdersRepository.findById(id);
    }
    public void purchasePurchaseOrdersDeleteById(int id) {
        purchasePurchaseOrdersRepository.deleteById(id);
    }
}