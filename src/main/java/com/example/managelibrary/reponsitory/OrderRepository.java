package com.example.managelibrary.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.managelibrary.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Order findByBook_IdAndUser_Id(Long buildingId, Long userId);
}
