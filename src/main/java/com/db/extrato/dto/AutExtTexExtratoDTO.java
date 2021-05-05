package com.db.extrato.dto;

import java.util.Date;

import com.db.extrato.enums.AutDsTipoOperacao;
import com.db.extrato.enums.AutStatus;
import com.db.extrato.enums.TipoContabil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class AutExtTexExtratoDTO {
	
	private Long autCdSequencial;
	
    private AutDsTipoOperacao autDsTipoOperacao;
	
    private AutStatus autStatus;
	
	private String autCdUsuarioSolicitacao;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date autDtSolicitacao;
	
	private String autCdUsuarioAutorizacao;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date autDtAutorizacao;
	
	private Long oldCdEmpresa;
	
	private Long oldCdCliente;
	
	private Long oldCdModalidade;
	
	private String oldCdOperOrigem;
	
	private Long cdEmpresa;
	
	private Long cdCliente;
	
	private Long cdModalidade;
	
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

    private Long cdMoeda;
	
    private Double quantidade;

	private Double vlContabil;

    private Double vlCorrigidoMe;

    private Double vlOperacaoMe;

    private Double vlOperacaoMn;

    private Double vlOperacaoEmp;
	
    private Double vlJurosMe;

    private Double vlJurosMn;

    private TipoContabil iiTipoContabil;
	
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
    
    private String dsNegocio;
	
	private String dsNegocioIngles;
	
	private String dsModalidade;
    
	private String dsProduto;

	private String nmMoeda;
	
}
