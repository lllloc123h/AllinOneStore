package com.aos.AOSBE.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Repository.EWalletsRepository;

@Service
public class EWalletsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private EWalletsRepository eWalletsRepository;

	public Page<EWallets> eWalletsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EWallets> spec = specBuilder.buildFilter(filters);
		return eWalletsRepository.findAll(spec, pageable);
	}

	@Transactional
	public EWallets eWalletsSave(EWallets eWallets) {
		return eWalletsRepository.save(eWallets);
	}

	public Optional<EWallets> eWalletsFindById(String id) {
		return eWalletsRepository.findById(id);
	}

	public Optional<EWallets> eWalletsFindByAccountEmail(String userEmail) {
		return eWalletsRepository.findByAccountEmail(userEmail);
	}

	@Transactional
	public void eWalletsDeleteById(String id) {
		eWalletsRepository.deleteById(id);
	}
}