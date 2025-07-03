package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.EWalletTransactions;
import com.aos.AOSBE.Repository.EWalletTransactionsRepository;

@Service
public class EWalletTransactionsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private EWalletTransactionsRepository eWalletTransactionsRepository;

	public Page<EWalletTransactions> eWalletTransactionsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EWalletTransactions> spec = specBuilder.buildFilter(filters);
		return eWalletTransactionsRepository.findAll(spec, pageable);
	}

	public List<EWalletTransactions> eWalletTransactionsFindByEwalletId(int id) {
		return eWalletTransactionsRepository.findByEwalletId(id);
	}

	public EWalletTransactions eWalletTransactionsFindByOrderID(String orderID) {
		return eWalletTransactionsRepository.findByOrderId(orderID).orElse(null);
	}

	@Transactional
	public EWalletTransactions eWalletTransactionsSave(EWalletTransactions eWalletTransactions) {
		return eWalletTransactionsRepository.save(eWalletTransactions);
	}

	public Optional<EWalletTransactions> eWalletTransactionsFindById(int id) {
		return eWalletTransactionsRepository.findById(id);
	}

	@Transactional
	public void eWalletTransactionsDeleteById(int id) {
		eWalletTransactionsRepository.deleteById(id);
	}
}