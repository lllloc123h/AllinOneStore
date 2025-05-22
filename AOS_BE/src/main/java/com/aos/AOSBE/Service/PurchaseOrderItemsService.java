package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class PurchaseOrderItemsService {
	@Autowired
    private PurchaseOrderItemsRepository purchasePurchaseOrderItemsRepository;

    public List<PurchaseOrderItems> purchasePurchaseOrderItemsFindAll() {
        return purchasePurchaseOrderItemsRepository.findAll();
    }
    public PurchaseOrderItems purchasePurchaseOrderItemsSave(PurchaseOrderItems purchasePurchaseOrderItems) {
        return purchasePurchaseOrderItemsRepository.save(purchasePurchaseOrderItems);
    }
    public Optional<PurchaseOrderItems> purchasePurchaseOrderItemsFindById(int id) {
        return purchasePurchaseOrderItemsRepository.findById(id);
    }
    public void purchasePurchaseOrderItemsDeleteById(int id) {
        purchasePurchaseOrderItemsRepository.deleteById(id);
    }
}