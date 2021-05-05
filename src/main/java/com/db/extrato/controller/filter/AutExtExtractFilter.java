package com.db.extrato.controller.filter;

import com.db.extrato.enums.AutStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class AutExtExtractFilter{

	private AutStatus autStatus;
	
	private String dtReferencia;
	
}
