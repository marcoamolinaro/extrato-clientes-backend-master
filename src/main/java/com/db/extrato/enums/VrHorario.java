package com.db.extrato.enums;

public enum VrHorario {
	MANHA("05:00"), 
	TARDE("10:00"), 
	NOITE("22:00");
	
	private String vrHorario;

	private VrHorario(String vrHorario) {
		this.vrHorario = vrHorario;
	}

	public String getVrHorario() {
		return vrHorario;
	}

	public void setVrHorario(String vrHorario) {
		this.vrHorario = vrHorario;
	}
	
	public static VrHorario getVrHorarioByString (String description)
	{
		if(VrHorario.MANHA.getVrHorario().equals(description))
		{
			return VrHorario.MANHA;
		} else if(VrHorario.TARDE.getVrHorario().equals(description))
		{
			return VrHorario.TARDE;
		} else if(VrHorario.NOITE.getVrHorario().equals(description))
		{
			return VrHorario.NOITE;
		}
		
		return null;
	}

}

