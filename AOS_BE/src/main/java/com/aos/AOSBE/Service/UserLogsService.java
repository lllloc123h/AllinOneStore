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
@Service
public class UserLogsService {
	@Autowired
    private UserLogsRepository userLogsRepository;

    public List<UserLogs> userLogsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return userLogsRepository.findAll(pageable).getContent();
    }
    public UserLogs userLogsSave(UserLogs userLogs) {
        return userLogsRepository.save(userLogs);
    }
    public Optional<UserLogs> userLogsFindById(int id) {
        return userLogsRepository.findById(id);
    }
    public void userLogsDeleteById(int id) {
        userLogsRepository.deleteById(id);
    }
}