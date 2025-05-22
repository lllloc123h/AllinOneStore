package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class ReviewsService {
	@Autowired
    private ReviewsRepository reviewsRepository;

    public List<Reviews> reviewsFindAll() {
        return reviewsRepository.findAll();
    }
    public Reviews reviewsSave(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }
    public Optional<Reviews> reviewsFindById(int id) {
        return reviewsRepository.findById(id);
    }
    public void reviewsDeleteById(int id) {
        reviewsRepository.deleteById(id);
    }
}