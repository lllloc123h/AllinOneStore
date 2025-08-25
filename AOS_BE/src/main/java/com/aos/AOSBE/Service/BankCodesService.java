package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class BankCodesService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
    private BankCodesRepository bankCodesRepository;

    public Page<BankCodes> bankCodesFindAll(int page, int size, Map<String, Object> filters) {
    	Pageable pageable = PageRequest.of(page, size);
		Specification<BankCodes> spec = specBuilder.buildFilter(filters);
		return bankCodesRepository.findAll(spec,pageable);
    }
    @Transactional
    public BankCodes bankCodesSave(BankCodes bankCodes) {
        return bankCodesRepository.save(bankCodes);
    }
    public Optional<BankCodes> bankCodesFindById(int id) {
        return bankCodesRepository.findById(id);
    }
    @Transactional
    public void bankCodesDeleteById(int id) {
        bankCodesRepository.deleteById(id);
    }
}