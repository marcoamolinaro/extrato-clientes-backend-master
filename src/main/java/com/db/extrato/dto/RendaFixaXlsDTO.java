package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendaFixaXlsDTO {

	private String mercadoria;
	
	private String indexador;
	
	private String dtEmissao;
	
	private String dtVcto;
	
	private Double vlrBase;
	
	private Double taxaOpe;
	
	private Double taxa252;
	
	private Double taxa360;
	
	private Double perc;
	
	private Double qtde;
	
	private Double ajusteFiscal;
	
	private Double ajusteMTM;
	
	private Double irProv;
	
}
