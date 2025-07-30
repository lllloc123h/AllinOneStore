package com.aos.AOSBE.Service;

import java.util.ArrayList;
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

import com.aos.AOSBE.DTOS.TrainDTO;
import com.aos.AOSBE.DTOS.UserProductEventsDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.UserProductEvents;
import com.aos.AOSBE.Mapper.UserProductEventsMapper;
import com.aos.AOSBE.Repository.UserProductEventsRepository;

@Service
public class UserProductEventsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private UserProductEventsRepository userProductEventsRepository;
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private UserProductEventsMapper userProductEventsMapper;

	public Page<UserProductEvents> userProductEventsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<UserProductEvents> spec = specBuilder.buildFilter(filters);
		return userProductEventsRepository.findAll(spec, pageable);
	}

	@Transactional
	public UserProductEvents userProductEventsSave(UserProductEvents userProductEvents) {
		return userProductEventsRepository.save(userProductEvents);
	}

	public Optional<UserProductEvents> userProductEventsFindById(int id) {
		return userProductEventsRepository.findById(id);
	}

//	public List<UserProductEvents> productItemsFindAllBoughtByGender(boolean gender) {
//		return userProductEventsRepository.findAllBoughtByGender(gender);
//	}

	public TrainDTO userProductEventsFindByUserBehaviour() {
		TrainDTO trainDTO = new TrainDTO();
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Accounts ac = accountsService.accountsFindByEmail(userEmail).orElse(null);
		trainDTO.setGender(ac.isGender() ? "Male" : "Female");
		trainDTO.setBirthday(ac.getBirthday().getYear());
		trainDTO.setListProductViewMostByGender(
				userProductEventsRepository.findAllBoughtByGender(ac.isGender(), "VIEWDETAIL"));
		trainDTO.setListProductBoughtMostByGender(
				userProductEventsRepository.findAllBoughtByGender(ac.isGender(), "ORDER"));
		trainDTO.setListProductAddToCartMostByGender(
				userProductEventsRepository.findAllBoughtByGender(ac.isGender(), "ADDTOCART"));

		trainDTO.setListProductViewByBirthday(userProductEventsRepository.findAllByBirthdayYearRange(
				ac.getBirthday().getYear() - 5, ac.getBirthday().getYear() + 5, "VIEWDETAIL"));

		trainDTO.setListProductBoughtByBirthday(userProductEventsRepository
				.findAllByBirthdayYearRange(ac.getBirthday().getYear() - 5, ac.getBirthday().getYear() + 5, "ORDER"));

		trainDTO.setListProductAddToCartByBirthday(userProductEventsRepository.findAllByBirthdayYearRange(
				ac.getBirthday().getYear() - 5, ac.getBirthday().getYear() + 5, "ADDTOCART"));

		// listProductByBirthday
		List<UserProductEventsDTOS> listBehaviour = new ArrayList<>();
		userProductEventsRepository.findAll().forEach(elementBehaviour -> {
			listBehaviour.add(userProductEventsMapper.mapper(elementBehaviour));
		});
		;
		return trainDTO;
	}

	@Transactional
	public void userProductEventsDeleteById(int id) {
		userProductEventsRepository.deleteById(id);
	}
}