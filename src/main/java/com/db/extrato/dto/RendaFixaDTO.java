package com.db.extrato.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendaFixaDTO {

	private String mercadoria;
	
	private String indexador;
	
	private Date dtEmissao;
	
	private Date dtVcto;
	
	private String vlrBase;
	
	private String taxaOpe;
	
	private String taxa252;
	
	private String taxa360;
	
	private String perc;
	
	private String qtde;
	
	private String ajusteFiscal;
	
	private String ajusteMTM;
	
	private Double irProv;

}