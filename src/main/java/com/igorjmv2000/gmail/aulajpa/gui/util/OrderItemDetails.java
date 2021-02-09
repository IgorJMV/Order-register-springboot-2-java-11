package com.igorjmv2000.gmail.aulajpa.gui.util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderItemDetails {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleDoubleProperty price;
	private SimpleIntegerProperty quantity;
	private SimpleDoubleProperty finalPrice;
	
	public OrderItemDetails(Integer id, String name, Double price, Integer quantity, Double finalPrice) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleDoubleProperty(price);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.finalPrice = new SimpleDoubleProperty(finalPrice);
	}

	public SimpleIntegerProperty idProperty() {
		return id;
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public SimpleDoubleProperty priceProperty() {
		return price;
	}

	public SimpleIntegerProperty quantityProperty() {
		return quantity;
	}

	public SimpleDoubleProperty finalPriceProperty() {
		return finalPrice;
	}
	
	
}
