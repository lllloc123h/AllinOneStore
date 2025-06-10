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
public class EWalletsService {
	@Autowired
    private EWalletsRepository eWalletsRepository;

    public List<EWallets> eWalletsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return eWalletsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public EWallets eWalletsSave(EWallets eWallets) {
        return eWalletsRepository.save(eWallets);
    }
    public Optional<EWallets> eWalletsFindById(int id) {
        return eWalletsRepository.findById(id);
    }
    @Transactional
    public void eWalletsDeleteById(int id) {
        eWalletsRepository.deleteById(id);
    }
}