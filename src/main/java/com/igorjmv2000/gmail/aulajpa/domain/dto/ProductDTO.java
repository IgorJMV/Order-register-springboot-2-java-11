package com.igorjmv2000.gmail.aulajpa.domain.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.igorjmv2000.gmail.aulajpa.domain.Product;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleDoubleProperty price;

	private Set<CategoryDTO> categories = new HashSet<>();

	private Set<OrderItemDTO> items = new HashSet<>();
	
	public ProductDTO() {}

	public ProductDTO(Integer id, String name, Double price) {
		try {
			this.id = new SimpleIntegerProperty(id);
		}catch(NullPointerException e) {}
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
	}
	
	public ProductDTO(Product entity) {
		try {
			this.id = new SimpleIntegerProperty(entity.getId());
		}catch(NullPointerException e) {}
		this.name = new SimpleStringProperty(entity.getName());
		this.price = new SimpleDoubleProperty(entity.getPrice());
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

	public String getName() {
		return name.get();
	}
	
	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public Double getPrice() {
		return price.get();
	}
	
	public SimpleDoubleProperty priceProperty() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = new SimpleDoubleProperty(price);
	}

	public Set<CategoryDTO> getCategories() {
		return categories;
	}

	public Set<OrderItemDTO> getItems() {
		return items;
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
		ProductDTO other = (ProductDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return id + ", " + name + ", R$" + String.format("%.2f", price);
	}
}
