package com.igorjmv2000.gmail.aulajpa.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.igorjmv2000.gmail.aulajpa.domain.Client;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.repositories.ClientRepository;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectAssociationException;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public ClientDTO insert(ClientDTO dto) {
		Client entity = fromEntity(dto);
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}
	
	public ClientDTO update(Integer id, ClientDTO dto) {
		findById(id);
		Client entity = clientRepository.findById(id).get();
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.setGenre(dto.getGenre());
		entity = clientRepository.save(entity);
		dto = new ClientDTO(entity);
		dto.getOrders().addAll(entity.getOrders().stream().map(x -> new OrderDTO(x)).collect(Collectors.toList()));
		return dto;
	}
	
	public ClientDTO findById(Integer id) {
		try {
			Client entity = clientRepository.findById(id).get();
			ClientDTO dto = new ClientDTO(entity);
			dto.getOrders().addAll(entity.getOrders().stream().map(x -> new OrderDTO(x)).collect(Collectors.toList()));
			return dto;
		}catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("client not found. Id: " + id);
		}
	}
	
	public List<ClientDTO> findAll(){
		List<Client> entities = clientRepository.findAll();
		List<ClientDTO> dtos = entities.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
		int i = 0;
		for(Client c : entities) {
			List<OrderDTO> orders = c.getOrders().stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
			dtos.get(i).getOrders().addAll(orders);
			i++;
		}
		
		return dtos;
	}
	
	public void deleteById(Integer id) {
		findById(id);
		try {
			clientRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new ObjectAssociationException("this client contains orders");
		}
	}
	
	protected static Client fromEntity(ClientDTO dto) {
		Client entity =  new Client(null, dto.getName(), dto.getBirthDate(), dto.getGenre());
		entity.getOrders().addAll(dto.getOrders().stream().map(x -> OrderService.fromEntity(x)).collect(Collectors.toList()));
		try {
			entity.setId(dto.getId());
		}catch(NullPointerException e) {}
		return entity;
	}
}
