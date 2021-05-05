package com.db.extrato.domain.extract;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.db.extrato.enums.AutDsTipoOperacao;
import com.db.extrato.enums.AutStatus;
import com.db.extrato.enums.TipoContabil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="AUT_EXT_TEX_EXTRATO", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class AutExtTexExtrato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autExtTexExtrato_seq")
	@SequenceGenerator(sequenceName = "SEQ_AUT_EXT_TEX_EXTRATO", allocationSize = 1, name = "autExtTexExtrato_seq")
	@Column(name = "AUT_CD_SEQUENCIAL")
	@EqualsAndHashCode.Include
	private Long autCdSequencial;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "AUT_DS_TIPO_OPERACAO")
    private AutDsTipoOperacao autDsTipoOperacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "AUT_STATUS")
    private AutStatus autStatus;
	
	@Column(name = "AUT_CD_USUARIO_SOLICITACAO")
	private String autCdUsuarioSolicitacao;
	
	@Column(name = "AUT_DT_SOLICITACAO")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull
	private Date autDtSolicitacao;
	
	@Column(name = "AUT_CD_USUARIO_AUTORIZACAO")
	private String autCdUsuarioAutorizacao;
	
	@Column(name = "AUT_DT_AUTORIZACAO")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotNull
	private Date autDtAutorizacao;
	
	@Column(name = "OLD_CD_EMPRESA")
	private Long oldCdEmpresa;
	
	@Column(name = "OLD_CD_CLIENTE")
	private Long oldCdCliente;
	
	@Column(name = "OLD_CD_MODALIDADE")
	private Long oldCdModalidade;
	
	@Column(name = "OLD_CD_OPER_ORIGEM")
	private String oldCdOperOrigem;
	
	@Column(name = "CD_EMPRESA")
	private Long cdEmpresa;
	
	@Column(name = "CD_CLIENTE")
	private Long cdCliente;
	
	@Column(name = "CD_MODALIDADE")
	private Long cdModalidade;
	
	@Column(name = "CD_OPER_ORIGEM")
	private String cdOperOrigem;
	
	@Column(name = "CD_CONTRATO")
	private String cdContrato;
	
	@Column(name = "DT_REFERENCIA")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dtReferencia;
	
	@Column(name = "DT_INICIO_OPER")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dtInicioOper;
	
	@Column(name = "DT_VENCIMENTO_OPER")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtVencimentoOper;
	
	@Column(name = "DT_LIQUIDACAO_OPER")
	@JsonFormat(pattern = "yyyy-MM-dd")
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
