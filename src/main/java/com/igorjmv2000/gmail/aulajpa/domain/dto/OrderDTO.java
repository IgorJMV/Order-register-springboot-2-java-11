package com.igorjmv2000.gmail.aulajpa.domain.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.igorjmv2000.gmail.aulajpa.domain.Order;
import com.igorjmv2000.gmail.aulajpa.domain.enums.OrderStatus;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private SimpleIntegerProperty id;
	private Date moment;
	private Integer status;
	
	private ClientDTO client;
	
	private Set<OrderItemDTO> items = new HashSet<>();
	
	public OrderDTO() {}

	public OrderDTO(Integer id, Date moment, OrderStatus status, ClientDTO client) {
		try {
			this.id = new SimpleIntegerProperty(id);
		}catch(NullPointerException e) {}
		this.moment = moment;
		this.status = status.getCod();
		this.client = client;
	}
	
	public OrderDTO(Order entity) {
		try {
			this.id = new SimpleIntegerProperty(entity.getId());
		}catch(NullPointerException e) {}
		this.moment = entity.getMoment();
		this.status = entity.getStatus().getCod();
		this.client = new ClientDTO(entity.getClient());
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

	public Date getMoment() {
		return moment;
	}
	
	public SimpleStringProperty momentProperty() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return new SimpleStringProperty(df.format(moment));
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public OrderStatus getStatus() {
		return OrderStatus.valueOf(status);
	}
	
	public SimpleStringProperty statusProperty() {
		return new SimpleStringProperty(OrderStatus.valueOf(status).getDescription());
	}

	public void setStatus(OrderStatus status) {
		this.status = status.getCod();
	}

	public ClientDTO getClient() {
		return client;
	}

	public SimpleStringProperty clientProperty() {
		return new SimpleStringProperty(client.getName());
	}
	
	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public Set<OrderItemDTO> getItems() {
		return items;
	}
	
	public Double totalPrice() {
		Double sum = 0.0;
		for(OrderItemDTO x : items) {
			sum += x.price();
		}
		
		return sum;
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
		OrderDTO other = (OrderDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
