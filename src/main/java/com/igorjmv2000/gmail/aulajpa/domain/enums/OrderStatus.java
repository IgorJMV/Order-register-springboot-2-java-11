package com.igorjmv2000.gmail.aulajpa.domain.enums;

public enum OrderStatus {
	WATTING_PAYMENT(1, "Aguardando pagamento"),
	PAID(2, "Pago"),
	CANCELED(3, "Cancelado");
	
	private int cod;
	private String description;
	
	private OrderStatus(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static OrderStatus valueOf(int cod) {
		for(OrderStatus x : OrderStatus.values()) {
			if(x.getCod() == cod) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid cod: " + cod);
	}
}
