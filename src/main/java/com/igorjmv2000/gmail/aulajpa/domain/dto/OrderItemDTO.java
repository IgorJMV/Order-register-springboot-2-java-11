package com.igorjmv2000.gmail.aulajpa.domain.dto;

import java.io.Serializable;

import com.igorjmv2000.gmail.aulajpa.domain.OrderItem;
import com.igorjmv2000.gmail.aulajpa.domain.dto.pk.OrderItemPKDTO;

public class OrderItemDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private OrderItemPKDTO id = new OrderItemPKDTO();
	private Integer quantity;
	private Double price;
	
	public OrderItemDTO() {}

	public OrderItemDTO(OrderDTO order, ProductDTO product, Integer quantity, Double price) {
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}
	
	public OrderItemDTO(OrderItem entity) {
		id.setOrder(new OrderDTO(entity.getOrder()));
		id.setProduct(new ProductDTO(entity.getProduct()));
		this.quantity = entity.getQuantity();
		this.price = entity.getPrice();
	}

	public ProductDTO getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(ProductDTO product) {
		id.setProduct(product);
	}
	
	public OrderDTO getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(OrderDTO order) {
		id.setOrder(order);
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double price() {
		return price * quantity;
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
		OrderItemDTO other = (OrderItemDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + quantity + "x) " + getProduct().getName() + " [R$" + String.format("%.2f", price()) + "]";
	}
}
