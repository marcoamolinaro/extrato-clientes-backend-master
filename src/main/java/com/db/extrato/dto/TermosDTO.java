package com.db.extrato.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermosDTO {

	private String posicao;
	
	private Date dataInicio;
	
	private Date dataVencto;
	
	private String mercadoria;
	
	private String unidadeNegociacao;
	
	private String bolsaReferencia;
	
	private Integer quantidade;
	
	private Double precoOperacao;
	
	private String moeda;
	
	private Double cotacaoSpot;
	
	private String contrato;
	
	private Double valorMTM;

}