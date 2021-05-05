package com.db.extrato.enums;

public enum VrReportType {
	PDF("PDF"), 
	EXCEL("EXCEL"), 
	AMBOS("AMBOS");
	
	private String vrReportType;

	private VrReportType(String vrReportType) {
		this.vrReportType = vrReportType;
	}

	public String getVrReportType() {
		return vrReportType;
	}

	public void setVrReportType(String vrReportType) {
		this.vrReportType = vrReportType;
	}
	
	public static VrReportType getVrReportTypeByString (String description)
	{
		if(VrReportType.PDF.getVrReportType().equals(description))
		{
			return VrReportType.PDF;
		}
		else if(VrReportType.EXCEL.getVrReportType().equals(description))
		{
			return VrReportType.EXCEL;
		}
		else if(VrReportType.AMBOS.getVrReportType().equals(description))
		{
			return VrReportType.AMBOS;
		} 
		
		return null;
	}

}
