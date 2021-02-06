package com.igorjmv2000.gmail.aulajpa.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.igorjmv2000.gmail.aulajpa.domain.Order;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderItemDTO;
import com.igorjmv2000.gmail.aulajpa.repositories.OrderRepository;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectAssociationException;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public OrderDTO insert(OrderDTO dto) {
		Order entity = fromEntity(dto);
		entity = orderRepository.save(entity);
		return new OrderDTO(entity);
	}
	
	public OrderDTO update(Integer id, OrderDTO dto) {
		findById(id);
		Order entity = orderRepository.findById(id).get();
		entity.setMoment(dto.getMoment());
		entity.setStatus(dto.getStatus());
		entity.setClient(ClientService.fromEntity(dto.getClient()));
		entity = orderRepository.save(entity);
		dto = new OrderDTO(entity);
		dto.getItems().addAll(entity.getItems().stream().map(x -> new OrderItemDTO(x)).collect(Collectors.toSet()));
		return dto;
	}
	
	public OrderDTO findById(Integer id) {
		try {
			Order entity = orderRepository.findById(id).get();
			OrderDTO dto = new OrderDTO(entity);
			dto.getItems().addAll(entity.getItems().stream().map(x -> new OrderItemDTO(x)).collect(Collectors.toSet()));
			return dto;
		}catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("order not found. Id: " + id);
		}
	}
	
	public List<OrderDTO> findAll(){
		List<Order> entities = orderRepository.findAll();
		List<OrderDTO> dtos = entities.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
		int i = 0;
		for(Order o : entities) {
			Set<OrderItemDTO> items = o.getItems().stream().map(x -> new OrderItemDTO(x)).collect(Collectors.toSet());
			dtos.get(i).getItems().addAll(items);
			i++;
		}
		return dtos;
	}
	
	public void deleteById(Integer id) {
		findById(id);
		try {
			orderRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new ObjectAssociationException("this order contains items");
		}
	}
	
	protected static Order fromEntity(OrderDTO dto) {
		Order entity =  new Order(null, dto.getMoment(), dto.getStatus(), ClientService.fromEntity(dto.getClient()));
		entity.getItems().addAll(dto.getItems().stream().map(x -> OrderItemService.fromEntity(x)).collect(Collectors.toSet()));
		try {
			entity.setId(dto.getId());
		}catch(NullPointerException e) {}
		return entity;
	}
}
