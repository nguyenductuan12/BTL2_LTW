package com.example.managelibrary.service;

import java.util.List;

import com.example.managelibrary.dto.OrderDto;
import com.example.managelibrary.entity.Order;

public interface OrderService {
	Order save(Order order);
	List<OrderDto> getAllOrders();
	Order findByBuildingIdAndUserId(Long idBuilding, Long idUser);
	void deleteOrderById(Long id);
}
