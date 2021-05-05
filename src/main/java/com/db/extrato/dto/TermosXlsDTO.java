package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermosXlsDTO {

	private String posicao;
	
	private String dataInicio;
	
	private String dataVencto;
	
	private String mercadoria;
	
	private String unidadeNegociacao;
	
	private String bolsaReferencia;
	
	private Double quantidade;
	
	private Double precoOperacao;
	
	private String moeda;
	
	private Double cotacaoSpot;
	
	private String contrato;
	
	private Double valorMTM;
	
}
