package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class BillOfMaterialsService {
	@Autowired
    private BillOfMaterialsRepository billBillOfMaterialsRepository;

    public List<BillOfMaterials> billBillOfMaterialsFindAll() {
        return billBillOfMaterialsRepository.findAll();
    }
    public BillOfMaterials billBillOfMaterialsSave(BillOfMaterials billBillOfMaterials) {
        return billBillOfMaterialsRepository.save(billBillOfMaterials);
    }
    public Optional<BillOfMaterials> billBillOfMaterialsFindById(int id) {
        return billBillOfMaterialsRepository.findById(id);
    }
    public void billBillOfMaterialsDeleteById(int id) {
        billBillOfMaterialsRepository.deleteById(id);
    }
}