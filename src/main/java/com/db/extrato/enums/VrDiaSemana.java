package com.db.extrato.enums;

import java.util.Calendar;

public enum VrDiaSemana {
	SEGUNDA("SEGUNDA"), 
	TERCA("TERÇA"), 
	QUARTA("QUARTA"), 
	QUINTA("QUINTA"), 
	SEXTA("SEXTA"),
    SABADO("SABADO"),
    DOMINGO("DOMINGO");

	private String vrDiaSemana;

	private VrDiaSemana(String vrDiaSemana) {
		this.vrDiaSemana = vrDiaSemana;
	}

	public String getVrDiaSemana() {
		return vrDiaSemana;
	}

	public void setVrDiaSemana(String vrDiaSemana) {
		this.vrDiaSemana = vrDiaSemana;
	}
	
	public static VrDiaSemana getVrDiaSemanaByString (String description)
	{
		if(VrDiaSemana.SEGUNDA.getVrDiaSemana().equals(description)) {
			return VrDiaSemana.SEGUNDA;
		} else if(VrDiaSemana.TERCA.getVrDiaSemana().equals(description)) {
			return VrDiaSemana.TERCA;
		} else if(VrDiaSemana.QUARTA.getVrDiaSemana().equals(description)) {
			return VrDiaSemana.QUARTA;
		} else if(VrDiaSemana.QUINTA.getVrDiaSemana().equals(description)) {
			return VrDiaSemana.QUINTA;
		} else if(VrDiaSemana.SEXTA.getVrDiaSemana().equals(description)) {
			return VrDiaSemana.SEXTA;
		} 
		
		return null;
	}
	
	public static VrDiaSemana getDiaSemana(int diaSemana) {
		switch (diaSemana) {
			case Calendar.MONDAY:
				return VrDiaSemana.SEGUNDA;
			case Calendar.TUESDAY:
				return VrDiaSemana.TERCA;
			case Calendar.WEDNESDAY:
				return VrDiaSemana.QUARTA;
			case Calendar.THURSDAY:
				return VrDiaSemana.QUINTA;
			case Calendar.FRIDAY:
				return VrDiaSemana.SEXTA;
			case Calendar.SATURDAY:
			  return VrDiaSemana.SABADO;
			case Calendar.SUNDAY:
			   return VrDiaSemana.DOMINGO;
		}
		return null;
	}
}

