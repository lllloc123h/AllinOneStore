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
public class UserProductEventsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
    private UserProductEventsRepository userProductEventsRepository;

    public List<UserProductEvents> userProductEventsFindAll(int page, int size, Map<String, Object> filters) {
    	Pageable pageable = PageRequest.of(page, size);
		Specification<Accounts> spec = specBuilder.buildFilter(filters);
		return userProductEventsRepository.findAll(spec,pageable);
    }
    @Transactional
    public UserProductEvents userProductEventsSave(UserProductEvents userProductEvents) {
        return userProductEventsRepository.save(userProductEvents);
    }
    public Optional<UserProductEvents> userProductEventsFindById(int id) {
        return userProductEventsRepository.findById(id);
    }
    @Transactional
    public void userProductEventsDeleteById(int id) {
        userProductEventsRepository.deleteById(id);
    }
}