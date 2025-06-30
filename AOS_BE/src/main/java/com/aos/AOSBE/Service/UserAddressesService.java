package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.UserAddresses;
import com.aos.AOSBE.Repository.UserAddressesRepository;

@Service
public class UserAddressesService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private UserAddressesRepository userAddressesRepository;

	public List<UserAddresses> userAddressesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<UserAddresses> spec = specBuilder.buildFilter(filters);
		return userAddressesRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public UserAddresses userAddressesSetDefaultAddress(int id, UserAddresses updated) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserAddresses findDefault = userAddressesRepository.findBAddressIsDefalut(true, email).orElse(null);
		if (findDefault != null) {
			findDefault.setDefault(false);
			updated.setDefault(true);
			userAddressesRepository.save(findDefault);
			userAddressesRepository.save(updated);
		}
		return updated;
	}

	public List<UserAddresses> userAddressesFindAllByUserEmail(String email) {
//		Pageable pageable = PageRequest.of(page, size);
		return userAddressesRepository.findByAccountsEmail(email);
	}

	@Transactional
	public UserAddresses userAddressesSave(UserAddresses userAddresses) {
		return userAddressesRepository.save(userAddresses);
	}

	public Optional<UserAddresses> userAddressesFindById(int id) {
		return userAddressesRepository.findById(id);
	}

	@Transactional
	public void userAddressesDeleteById(int id) {
		userAddressesRepository.deleteById(id);
	}
}