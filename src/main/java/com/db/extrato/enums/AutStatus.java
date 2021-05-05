package com.db.extrato.enums;

public enum AutStatus {
	
	REJEITADO("REJEITADO"),
	APROVADO("APROVADO"),
	PENDENTE("PENDENTE");
	
	String name;

	private AutStatus(String name) {
		this.name = name;
	}
	
}
