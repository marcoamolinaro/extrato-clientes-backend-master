package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapXlsDTO {

	private String nrContrato;
	
	private Double vlrBase;
	
	private String dtInicio;
	
	private String dtVcto;
	
	private String indexParte;
	
	private Double indexPartePerc;
	
	private Double taxaParte252;
	
	private Double taxaParte360;
	
	private Double valorParte;
	
	private String indexContraParte;
	
	private Double indexContraPartePerc;
	
	private Double taxaContraParte252;
	
	private Double taxaContraParte360;
	
	private Double valorContraParte;
	
	private Double ajusteFiscal;

	private Double ajusteMTM;
	
}
