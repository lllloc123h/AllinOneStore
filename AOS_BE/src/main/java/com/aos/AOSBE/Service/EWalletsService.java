package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Repository.EWalletsRepository;

@Service
public class EWalletsService {
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private EWalletsRepository eWalletsRepository;

	public List<EWallets> eWalletsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EWallets> spec = specBuilder.buildFilter(filters);
		return eWalletsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public EWallets eWalletsSave(EWallets eWallets) {
		return eWalletsRepository.save(eWallets);
	}

	public EWallets eWalletsFindById(int id) {
		return eWalletsRepository.findById(id).orElse(null);
	}

//	public EWallets eWalletsFindByAccountEmail(String userEmail) {
//		return eWalletsRepository.findByAccountEmail(userEmail).orElse(null);
//	}

	public Optional<EWallets> eWalletsFindByAccountId(int id) {
		return eWalletsRepository.findById(id);
	}

	@Transactional
	public void eWalletsDeleteById(int id) {
		eWalletsRepository.deleteById(id);
	}
}