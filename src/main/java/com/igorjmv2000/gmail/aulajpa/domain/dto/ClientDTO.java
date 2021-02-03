package com.igorjmv2000.gmail.aulajpa.domain.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.igorjmv2000.gmail.aulajpa.domain.Client;
import com.igorjmv2000.gmail.aulajpa.domain.enums.Genre;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private Date birthDate;
	private Integer genre;

	private List<OrderDTO> orders = new ArrayList<>();
	
	public ClientDTO() {}

	public ClientDTO(Integer id, String name, Date birthDate, Genre genre) {
		try {
			this.id = new SimpleIntegerProperty(id);
		}catch(NullPointerException e) {}
		this.name = new SimpleStringProperty(name);
		this.birthDate = birthDate;
		this.genre = genre.getCod();
	}
	
	public ClientDTO(Client entity) {
		try {
			this.id = new SimpleIntegerProperty(entity.getId());
		}catch(NullPointerException e) {}
		this.name = new SimpleStringProperty(entity.getName());
		this.birthDate = entity.getBirthDate();
		this.genre = entity.getGenre().getCod();
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

	public Date getBirthDate() {
		return birthDate;
	}
	
	public SimpleStringProperty birthDateProperty() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return new SimpleStringProperty(df.format(birthDate));
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Genre getGenre() {
		return Genre.valueOf(genre);
	}
	
	public SimpleStringProperty genreProperty() {
		return new SimpleStringProperty(Genre.valueOf(genre).getDescription());
	}

	public void setGenre(Genre genre) {
		this.genre = genre.getCod();
	}

	public List<OrderDTO> getOrders() {
		return orders;
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
		ClientDTO other = (ClientDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
