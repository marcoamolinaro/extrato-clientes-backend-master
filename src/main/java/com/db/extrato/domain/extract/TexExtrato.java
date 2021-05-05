package com.db.extrato.domain.extract;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.db.extrato.enums.TipoContabil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TEX_EXTRATO", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TexExtrato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	private TexEntratoId texEntratoId;
	
	@Column(name = "CD_EMPRESA", insertable = false, updatable = false)
	private Long cdEmpresa;
	
	@Column(name = "CD_CLIENTE", insertable = false, updatable = false)
	private Long cdCliente;
	
	@Column(name = "CD_MODALIDADE", insertable = false, updatable = false)
	private Long cdModalidade;
	
	@Column(name = "CD_OPER_ORIGEM", insertable = false, updatable = false)
	private String cdOperOrigem;
	
	@Column(name = "CD_CONTRATO")
	private String cdContrato;
	
	@Column(name = "DT_REFERENCIA")
	@JsonFormat(pattern = "dd-MM-yy")
	private Date dtReferencia;
	
	@Column(name = "DT_INICIO_OPER")
	@JsonFormat(pattern = "dd-MM-yy")
	private Date dtInicioOper;
	
	@Column(name = "DT_VENCIMENTO_OPER")
	@JsonFormat(pattern = "dd-MM-yy")
    private Date dtVencimentoOper;
	
	@Column(name = "DT_LIQUIDACAO_OPER")
	@JsonFormat(pattern = "dd-MM-yy")
    private Date dtLiquidacaoOper;
	
	@Column(name = "CD_GRPROD")
    private Long cdGrprod;

	@Column(name = "CD_FAMPROD")
    private Long cdFamprod;
	
	@Column(name = "CD_PRODUTO")
	private Long cdProduto;

	@Column(name = "SG_SISTEMA")
    private String sgSistema;

	@Column(name = "SG_MODULO")
	private String sgModulo;

	@Column(name = "CD_MOEDA")
    private Long cdMoeda;

	@Column(name = "QUANTIDADE")
    private Double quantidade;

	@Column(name="VL_CONTABIL")
	private Double vlContabil;

	@Column(name="VL_CORRIGIDO_ME")
    private Double vlCorrigidoMe;

	@Column(name="VL_OPERACAO_ME")
    private Double vlOperacaoMe;

	@Column(name="VL_OPERACAO_MN")
    private Double vlOperacaoMn;

	@Column(name="VL_OPERACAO_EMP")
    private Double vlOperacaoEmp;
	
	@Column(name="VL_Juros_ME")
    private Double vlJurosMe;

	@Column(name="VL_Juros_MN")	
    private Double vlJurosMn;

	@Enumerated(EnumType.STRING)
	@Column(name="II_TIPO_CONTABIL")	
    private TipoContabil iiTipoContabil;
	
	@Column(name="CD_CGCCPF")	
    private String cdCgcCpf;
	
	@Column(name="NM_CLIENTE")	
    private String nmCliente;

	@Column(name="DC_END1")	
    private String dcEnd1;

	@Column(name="DC_END2")	
    private String dcEnd2;

	@Column(name="DC_END3")	
    private String dcEnd3;

	@Column(name="CD_PAIS")	
    private Long cdPais;

	@Column(name="NOME_AOSCUIDADOS")	
    private String nomeAosCuidados;
    
	@Column(name="DS_OBN")	
    private String dsObn;

	@Column(name="NM_EMPRESA")	
    private String nmEmpresa;

	@Column(name="CD_FILIAL")		
    private Long cdFilial;

	@Column(name="NM_INDEXADOR")			
    private String nmIndexador;

	@Column(name="CD_GARANTIA")				
    private String cdGarantia;
}
