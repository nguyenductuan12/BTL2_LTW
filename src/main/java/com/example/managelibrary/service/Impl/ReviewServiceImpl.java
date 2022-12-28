package com.example.managelibrary.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.managelibrary.entity.Review;
import com.example.managelibrary.reponsitory.ReviewRepository;
import com.example.managelibrary.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
    private ReviewRepository reviewRepository;
	
	@Override
	public Review create(Review review) {
		return reviewRepository.save(review);
	}

}
