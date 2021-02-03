package com.igorjmv2000.gmail.aulajpa.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.igorjmv2000.gmail.aulajpa.domain.Category;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty description;
	
	private Set<ProductDTO> products = new HashSet<>();
	
	public CategoryDTO() {}

	public CategoryDTO(Integer id, String description) {
		try {
			this.id = new SimpleIntegerProperty(id);
		}catch(NullPointerException e) {}
		this.description = new SimpleStringProperty(description);
	}
	
	public CategoryDTO(Category entity) {
		try {
			this.id = new SimpleIntegerProperty(entity.getId());
		}catch(NullPointerException e) {}
		this.description = new SimpleStringProperty(entity.getDescription());
	}

	public Integer getId() {
		return id.get();
	}
	
	public SimpleIntegerProperty idProperty() {
		return id;
	}

	public void setId(Integer id) {
		this.id = new SimpleIntegerProperty(id);
	}

	public String getDescription() {
		return description.get();
	}
	
	public SimpleStringProperty descriptionProperty() {
		return description;
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}

	public Set<ProductDTO> getProducts() {
		return products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDTO other = (CategoryDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return description.get();
	}
}
