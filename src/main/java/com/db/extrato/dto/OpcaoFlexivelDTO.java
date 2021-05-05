package com.db.extrato.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoFlexivelDTO {

	private String tipoOpcao;

	private String posicao;

	private String moeda;

	private Double vlrBaseOrig;

	private Double vlrBaseAtual;

	private Double premio;

	private Date dtPremio;

	private Double precoExercicio;

	private Date dtReg;

	private Date dtVcto;

	private String tipoExercicio;

	private Date dtFixing;

	private String fonteInfo;
	
	private String localReg;

	private String codCetip;

	private Double valorMTM;
	
}
