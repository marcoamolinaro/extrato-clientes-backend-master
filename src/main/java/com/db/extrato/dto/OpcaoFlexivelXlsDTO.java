package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoFlexivelXlsDTO {

	private String tipoOpcao;
	
	private String posicao;
	
	private String moeda;
	
	private Double vlrBaseOrig;
	
	private Double vlrBaseAtual;
	
	private Double premio;
	
	private String dtPremio;
	
	private Double precoExercicio;
	
	private String dtReg;
	
	private String dtVcto;
	
	private String tipoExercicio;
	
	private String dtFixing;
	
	private String fonteInfo;
	
	private String localReg;
	
	private String codCetip;
	
	private Double valorMTM;
	
}
