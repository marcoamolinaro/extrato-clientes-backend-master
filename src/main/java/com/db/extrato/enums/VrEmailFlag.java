package com.db.extrato.enums;

public enum VrEmailFlag {
	SIM("SIM"), 
	NAO("NAO");
	
	private String vrEmailFlag;

	private VrEmailFlag(String vrEmailFlag) {
		this.vrEmailFlag = vrEmailFlag;
	}

	public String getVrEmailFlag() {
		return vrEmailFlag;
	}

	public void setVrEmailFlag(String vrEmailFlag) {
		this.vrEmailFlag = vrEmailFlag;
	}	
}
