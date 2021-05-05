package com.db.extrato.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TexExtratoDTO {
	
	private Long cdEmpresa;
	
	private String cdCliente;
	
	private Long cdModalidade;
	
	private String dsNegocio;
	
	private String dsNegocioIngles;
	
	private String cdOperOrigem;
	
	private String cdContrato;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dtReferencia;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dtInicioOper;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtVencimentoOper;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtLiquidacaoOper;
	
    private Long cdGrprod;

    private Long cdFamprod;
	
	private Long cdProduto;

    private String sgSistema;

	private String sgModulo;

    private Integer cdMoeda;

    private Double qtde;

	private Double vlContabil;

    private Double vlCorrigidoMe;

    private Double vlOperacaoMe;

    private Double vlOperacaoMn;

    private Double vlOperacaoEmp;
	
    private Double vlJurosMe;

    private Double vlJurosMn;

    private String iiTipoContabil;
	
    private String cdCgcCpf;
	
    private String nmCliente;

    private String dcEnd1;

    private String dcEnd2;

    private String dcEnd3;

    private Long cdPais;

    private String nomeAosCuidados;
    
    private String dsObn;

    private String nmEmpresa;

    private Long cdFilial;

    private String nmIndexador;

    private String cdGarantia;
    
    private String moeda;
    
    private String dsProduto;

    private String displayQtde;
    
    private String dsModalidade;
    
    private String displaySldMe;

    private String displaySldMn;
    
    
}
