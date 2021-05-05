package com.db.extrato.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SwapFluxoDTO implements Serializable {

	private static final long serialVersionUID = 3509954052924052511L;

	private String nrContrato;

	private String vlrBase;

	private String dtInicio;

	private String dtVcto;

	private String indexParte;

	private String indexPartePerc;

	private String taxaParte360;

	private String valorParte;

	private String indexContraParte;

	private String indexContraPartePerc;

	private String taxaContraParte360;

	private String valorContraParte;

	private String ajusteFiscal;

	private String ajusteMTM;

	private Integer idOperacao;

	private Integer nrParcela;

	private String taxaParcela252;

	private String taxaParcela360;

	private String dtInicioParcela;

	private String dtVctoParcela;

}