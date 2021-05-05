package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NdfXlsDTO {

	private String nrContrato;
	
	private Double vlrBase;
	
	private String dtInicio;
	
	private String dtVcto;
	
	private String moedaRef;
	
	private Double cotTermo;
	
	private Double taxPre252;
	
	private String posicao;
	
	private Double fiscalParte;
	
	private Double fiscalContraParte;
	
	private Double ajusteFiscal;
	
	private Double ajusteMTM;
	
	private Double vlrNotional;
	
}
