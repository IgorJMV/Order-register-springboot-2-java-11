package com.igorjmv2000.gmail.aulajpa.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igorjmv2000.gmail.aulajpa.domain.OrderItem;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderItemDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.repositories.OrderItemRepository;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectNotFoundException;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderItemDTO insert(OrderItemDTO dto) {
		OrderItem entity = fromEntity(dto);
		entity = orderItemRepository.save(entity);
		return new OrderItemDTO(entity);
	}
	
	public OrderItemDTO find(ProductDTO product, OrderDTO order) {
		try {
			OrderItem entity = orderItemRepository.findAll().stream().filter(oi -> (oi.getProduct().equals(ProductService.fromEntity(product)) && oi.getOrder().equals(OrderService.fromEntity(order)))).collect(Collectors.toList()).get(0);
			return new OrderItemDTO(entity);
		}catch(IndexOutOfBoundsException e) {
			throw new ObjectNotFoundException("OrderItem not found");
		}
	}
	
	public void delete(OrderItemDTO dto) {
		try {
			OrderItem entity = orderItemRepository.findAll().stream().filter(oi -> (oi.getProduct().equals(ProductService.fromEntity(dto.getProduct())) && oi.getOrder().equals(OrderService.fromEntity(dto.getOrder())))).collect(Collectors.toList()).get(0);
			orderItemRepository.delete(entity);
		}catch(IndexOutOfBoundsException e) {
			throw new ObjectNotFoundException("OrderItem not found");
		}
	}
	
	protected static OrderItem fromEntity(OrderItemDTO dto) {
		return new OrderItem(OrderService.fromEntity(dto.getOrder()), ProductService.fromEntity(dto.getProduct()), dto.getQuantity(), dto.getPrice());
	}
}
