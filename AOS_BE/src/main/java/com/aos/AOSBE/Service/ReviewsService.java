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
public class ReviewsService {
	@Autowired
    private ReviewsRepository reviewsRepository;

    public List<Reviews> reviewsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return reviewsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Reviews reviewsSave(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }
    public Optional<Reviews> reviewsFindById(int id) {
        return reviewsRepository.findById(id);
    }
    @Transactional
    public void reviewsDeleteById(int id) {
        reviewsRepository.deleteById(id);
    }
}