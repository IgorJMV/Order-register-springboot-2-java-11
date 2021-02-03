package com.igorjmv2000.gmail.aulajpa.services;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.igorjmv2000.gmail.aulajpa.domain.Product;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderItemDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.repositories.ProductRepository;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectAssociationException;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public ProductDTO insert(ProductDTO dto) {
		Product entity = fromEntity(dto);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}
	
	public ProductDTO update(Integer id, ProductDTO dto) {
		findById(id);
		Product entity = productRepository.findById(id).get();
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity = productRepository.save(entity);
		dto = new ProductDTO(entity);
		dto.getItems().addAll(entity.getItems().stream().map(x -> new OrderItemDTO(x)).collect(Collectors.toSet()));
		dto.getCategories().addAll(entity.getCategories().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toSet()));
		return dto;
	}
	
	public ProductDTO findById(Integer id) {
		try {
			Product entity = productRepository.findById(id).get();
			ProductDTO dto = new ProductDTO(entity);
			dto.getCategories().addAll(entity.getCategories().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toSet()));
			dto.getItems().addAll(entity.getItems().stream().map(x -> new OrderItemDTO(x)).collect(Collectors.toSet()));
			return dto;
		}catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("product not found. Id: " + id);
		}
	}
	
	public void deleteById(Integer id) {
		findById(id);
		try {
			productRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new ObjectAssociationException("this product is contained in an orderItem");
		}
	}
	
	protected static Product fromEntity(ProductDTO dto) {
		Product entity =  new Product(null, dto.getName(), dto.getPrice());
		entity.getCategories().addAll(dto.getCategories().stream().map(x -> CategoryService.fromEntity(x)).collect(Collectors.toSet()));
		entity.getItems().addAll(dto.getItems().stream().map(x -> OrderItemService.fromEntity(x)).collect(Collectors.toSet()));
		try {
			entity.setId(dto.getId());
		}catch(NullPointerException e) {}
		return entity;
	}
}
