package com.igorjmv2000.gmail.aulajpa.services;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.igorjmv2000.gmail.aulajpa.domain.Category;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.repositories.CategoryRepository;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectAssociationException;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = fromEntity(dto);
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}
	
	public CategoryDTO update(Integer id, CategoryDTO dto) {
		findById(id);
		Category entity = categoryRepository.findById(id).get();
		entity.setDescription(dto.getDescription());
		entity = categoryRepository.save(entity);
		dto = new CategoryDTO(entity);
		dto.getProducts().addAll(entity.getProducts().stream().map(x -> new ProductDTO(x)).collect(Collectors.toSet()));
		return dto;
	}
	
	public CategoryDTO findById(Integer id) {
		try {
			Category entity = categoryRepository.findById(id).get();
			CategoryDTO dto = new CategoryDTO(entity);
			dto.getProducts().addAll(entity.getProducts().stream().map(x -> new ProductDTO(x)).collect(Collectors.toList()));
			return dto;
		}catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("category not found. Id: " + id);
		}
	}
	
	public void deleteById(Integer id) {
		findById(id);
		try {
			categoryRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new ObjectAssociationException("this category contains products");
		}
	}
	
	protected static Category fromEntity(CategoryDTO dto) {
		Category entity =  new Category(null, dto.getDescription());
		entity.getProducts().addAll(dto.getProducts().stream().map(x -> ProductService.fromEntity(x)).collect(Collectors.toSet()));
		try {
			entity.setId(dto.getId());
		}catch(NullPointerException e) {}
		return entity;
	}
}
