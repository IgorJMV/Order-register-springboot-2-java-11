package com.igorjmv2000.gmail.aulajpa.domain.enums;

public enum Genre {
	MALE(1, "Masculino"),
	FEMALE(2, "Feminino");
	
	private int cod;
	private String description;
	
	private Genre(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static Genre valueOf(int cod) {
		for(Genre x : Genre.values()) {
			if(x.getCod() == cod)
				return x;
		}
		
		throw new IllegalArgumentException("Invalid cod: " + cod);
	}
}
