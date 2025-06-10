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
public class EWalletTransactionsService {
	@Autowired
    private EWalletTransactionsRepository eWalletTransactionsRepository;

    public List<EWalletTransactions> eWalletTransactionsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return eWalletTransactionsRepository.findAll(pageable).getContent();
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