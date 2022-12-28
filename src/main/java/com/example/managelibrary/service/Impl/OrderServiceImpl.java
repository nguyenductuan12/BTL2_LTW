package com.example.managelibrary.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.managelibrary.converter.OrderConverter;
import com.example.managelibrary.dto.OrderDto;
import com.example.managelibrary.entity.Order;
import com.example.managelibrary.reponsitory.OrderRepository;
import com.example.managelibrary.service.OrderService;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderConverter orderConverter;
	@Autowired
	private OrderRepository orderRepository;
	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}
	@Override
	public List<OrderDto> getAllOrders() {
		List<OrderDto> results = new ArrayList<>();
		results = orderRepository.findAll().stream().map(item -> orderConverter.convertEntityToDto(item)).collect(Collectors.toList());
		return results;
	}
	@Override
	public Order findByBuildingIdAndUserId(Long idBuilding, Long idUser) {
		return orderRepository.findByBook_IdAndUser_Id(idBuilding, idUser);
	}
	@Override
	public void deleteOrderById(Long id) {
		orderRepository.deleteById(id);
	}

	

	
}
