package com.db.extrato.enums;

public enum TipoContabil {
	
	A("ATIVO"),
	P("PASSIVO"),
	C("COMPENSACAO"),
	T("TODOS");
	String name;

	private TipoContabil(String name) {
		this.name = name;
	}
	
}
