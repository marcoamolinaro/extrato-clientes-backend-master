package com.db.extrato.enums;

public enum VrPeriodo {
	DIARIO("DIARIO", "Diário"), 
	SEMANAL("SEMANAL", "Semanal"), 
	MENSAL("MENSAL", "Mensal"), NENHUM("", "");
	
	private String period;
	
	private String value;

	VrPeriodo(String period, String value) {
		this.period = period;
		this.value = value;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static VrPeriodo getVrPeriodoByString (String description)
	{
		if(VrPeriodo.DIARIO.getPeriod().equals(description))
		{
			return VrPeriodo.DIARIO;
		}
		else if(VrPeriodo.SEMANAL.getPeriod().equals(description))
		{
			return VrPeriodo.SEMANAL;
		}
		else if(VrPeriodo.MENSAL.getPeriod().equals(description))
		{
			return VrPeriodo.MENSAL;
		} if (VrPeriodo.NENHUM.getPeriod().equals(description)) {
			return NENHUM;
		}
		
		return null;
	}
}

