package com.example.managelibrary.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.managelibrary.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
