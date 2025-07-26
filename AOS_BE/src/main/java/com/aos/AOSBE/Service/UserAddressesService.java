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
	    // Lấy tên
	    String provinceName = userAddresses.getProvince();
	    String districtName = userAddresses.getDistrict();
	    String wardName = userAddresses.getWard();

	    // Mapping tên → mã GHN
	    int provinceId = ghnAddressService.getProvinceIdByName(provinceName);
	    int districtId = ghnAddressService.getDistrictIdByName(provinceId, districtName);
	    String wardCode = ghnAddressService.getWardCodeByName(districtId, wardName);

	    // Gán lại thông tin GHN
	    userAddresses.setGhnProvinceId(provinceId);
	    userAddresses.setGhnDistrictId(districtId);
	    userAddresses.setGhnWardCode(wardCode);

	    // Đảm bảo tên cũng đồng bộ (phòng trường hợp bị viết sai format)
	    userAddresses.setProvince(ghnAddressService.getProvinceNameById(provinceId));
	    userAddresses.setDistrict(ghnAddressService.getDistrictNameById(provinceId, districtId));
	    userAddresses.setWard(ghnAddressService.getWardNameByCode(districtId, wardCode));

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