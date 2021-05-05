package com.db.extrato.enums;

public enum ReportType {

	CONSOLIDATE("CONSOLIDATE"),
	ANALYTIC("ANALYTIC"),
	MANUTENCAO("MANUTENCAO");
  
	String type;

	private ReportType(String type) {
		this.type = type;
	}
	
}
