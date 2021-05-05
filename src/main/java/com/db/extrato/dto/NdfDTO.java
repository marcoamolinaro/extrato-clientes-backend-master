package com.db.extrato.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NdfDTO {

	private String nrContrato;

	private String vlrBase;

	private Date dtInicio;

	private Date dtVcto;

	private String moedaRef;

	private String cotTermo;

	private String taxPre252;

	private String posicao;

	private String fiscalParte;

	private String fiscalContraParte;

	private String ajusteFiscal;

	private String ajusteMTM;

	private String vlrNotional;

}