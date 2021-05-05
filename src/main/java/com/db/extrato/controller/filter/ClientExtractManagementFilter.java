package com.db.extrato.controller.filter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientExtractManagementFilter {
	
	private Long cdCliBr;
	
	private String vrPeriodo;
	
	private String nmCli;
}
