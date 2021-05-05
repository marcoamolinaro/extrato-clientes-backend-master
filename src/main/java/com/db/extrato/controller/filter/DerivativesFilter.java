package com.db.extrato.controller.filter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class DerivativesFilter {

	private String data;
	
	private List<Long> cdClientes;
	
}
