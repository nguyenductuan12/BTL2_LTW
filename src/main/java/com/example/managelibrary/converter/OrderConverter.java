package com.example.managelibrary.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.managelibrary.dto.OrderDto;
import com.example.managelibrary.entity.Order;

import org.modelmapper.ModelMapper;

@Component
public class OrderConverter {
	@Autowired
	private ModelMapper modelMapper;
	
	public OrderDto convertEntityToDto(Order order) {
		OrderDto orderDto = modelMapper.map(order, OrderDto.class);
		Float total  = order.getQuantity()*order.getBook().getPrice();
		orderDto.setTotal(total);
		return orderDto;
	}
}
