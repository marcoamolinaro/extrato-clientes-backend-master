package com.db.extrato.enums;

public enum ReportName {

	MONTHLY_REPORT("MONTHLY_REPORT"),
	ANNUAL_REPORT("ANNUAL_REPORT"),
	FULL("FULL");
	
	String name;

	private ReportName(String name) {
		this.name = name;
	}
	
}
