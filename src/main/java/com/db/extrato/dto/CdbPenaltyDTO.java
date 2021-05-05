package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CdbPenaltyDTO {

	String nmVeiculoLegal;
	
	String nmContraparte;
	
	String documentoContraparte;
	
	String nmProduto;
	
	String nmIndexador;
	
	String data;
	
	String valorBruto;
	
	String valorProvisaoIOF;
	
	String valorProvisaoIR;
	
	String valorLiquido;
	
	String valorRendimento;
	
	String valorAplicacao;
	
	String valorResgateBruto;
	
	String valorIOFResgate;
	
	String valorIRResgate;
	
	String valorResgateLiquido;

}