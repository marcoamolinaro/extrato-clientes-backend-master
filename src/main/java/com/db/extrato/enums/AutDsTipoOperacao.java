package com.db.extrato.enums;

public enum AutDsTipoOperacao {
	
	INSERT("INSERT"),
	UPDATE("UPDATE"),
	DELETE("DELETE");
	
	String name;

	private AutDsTipoOperacao(String name) {
		this.name = name;
	}
	
}
