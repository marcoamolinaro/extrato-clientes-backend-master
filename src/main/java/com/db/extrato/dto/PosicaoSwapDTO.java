package com.db.extrato.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class PosicaoSwapDTO {

	private String nrContrato;
	
	private BigDecimal vlrBase;

	private Date dtInicio;

	private Date dtVecto;

	private String indexParte;

	private double percIndParte;

	private double tx360Parte;

	private double fiscalParte;

	private String indexCp;

	private double percINDCP;

	private double tx360CP;

	private double fiscalCP;

	private double ajusteFiscal;

	private double ajusteMtm;

	private Integer idOperacao;

}
