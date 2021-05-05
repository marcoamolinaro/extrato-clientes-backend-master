package com.db.extrato.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapDTO {

	private String nrContrato;

	private String vlrBase;

	private Date dtInicio;

	private Date dtVcto;

	private String indexParte;

	private String indexPartePerc;

	private String taxaParte252;

	private String taxaParte360;

	private String valorParte;

	private String indexContraParte;

	private String indexContraPartePerc;

	private String taxaContraParte252;

	private String taxaContraParte360;

	private String valorContraParte;

	private String ajusteFiscal;

	private String ajusteMTM;

	private Integer nrParcela;

	private Date dtInicioParcela;

	private Date dtVctoParcela;
	
}
