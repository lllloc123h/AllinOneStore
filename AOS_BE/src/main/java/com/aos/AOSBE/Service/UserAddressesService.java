package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	@Autowired
	private GhnAddressService ghnAddressService;

	public Page<UserAddresses> userAddressesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<UserAddresses> spec = specBuilder.buildFilter(filters);
		return userAddressesRepository.findAll(spec, pageable);
	}

	public UserAddresses userAddressesSetDefaultAddress(int id, UserAddresses selectedAddress) {
	    String email = selectedAddress.getAccounts().getEmail();

	    // 1. Tắt hết các địa chỉ mặc định của user này
	    List<UserAddresses> addresses = userAddressesRepository.findByAccountsEmail(email);
	    for (UserAddresses addr : addresses) {
	        addr.setDefault(addr.getId() == id);
	    }

	    userAddressesRepository.saveAll(addresses);

	    return selectedAddress;
	}


	public List<UserAddresses> userAddressesFindAllByUserEmail(String email) {
//		Pageable pageable = PageRequest.of(page, size);
		return userAddressesRepository.findByAccountsEmail(email);
	}

	@Transactional
	public UserAddresses userAddressesSave(UserAddresses userAddresses) {
	    Integer provinceId = userAddresses.getGhnProvinceId();
	    Integer districtId = userAddresses.getGhnDistrictId();
	    String wardCode = userAddresses.getGhnWardCode();

	    // Check null để tránh lỗi runtime
	    if (provinceId != null && districtId != null && wardCode != null) {
	        String provinceName = ghnAddressService.getProvinceNameById(provinceId);
	        String districtName = ghnAddressService.getDistrictNameById(provinceId, districtId);
	        String wardName = ghnAddressService.getWardNameByCode(districtId, wardCode);

	        userAddresses.setProvince(provinceName);
	        userAddresses.setDistrict(districtName);
	        userAddresses.setWard(wardName);
	    }

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