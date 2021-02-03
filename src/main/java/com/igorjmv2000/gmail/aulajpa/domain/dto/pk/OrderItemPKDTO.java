package com.igorjmv2000.gmail.aulajpa.domain.dto.pk;

import java.io.Serializable;

import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;

public class OrderItemPKDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private OrderDTO order;
	private ProductDTO product;
	
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		OrderItemPKDTO other = (OrderItemPKDTO) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	
}
