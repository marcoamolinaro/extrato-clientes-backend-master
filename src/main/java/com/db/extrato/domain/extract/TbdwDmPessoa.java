package com.db.extrato.domain.extract;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TBDW_DIM_PESSOA", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TbdwDmPessoa implements Serializable{
  
  private static final long serialVersionUID = -3128975636717802121L;

  @Id
  @Column(name = "CD_PESSOA")
  private Long cdPessoa;
  
  @Column(name = "NM_PESSOA")
  private String nmPessoa;

  @Column(name = "NM_PESSOA_ABREV")
  private String nmPessoaAbrev;

  @Column(name = "CD_GEC_BR")
  private Long cdGecBr;

  @Column(name = "NM_GEC_BR")
  private String nmGecBr;

  @Column(name = "CD_ATE_BR")
  private Long cdAteBr;

  @Column(name = "NM_ATE_BR")
  private String nmAteBr;
  
  @Column(name = "CD_PESSOA_CCDB")
  private Long cdPessoaCcdb;

  @Column(name = "CD_ATE_AL")
  private Long cdAteAl;
  
  @Column(name = "NM_ATE_AL")
  private String nmAteAl;
  
  @Column(name = "CD_PAIS")
  private Long cdPais;
  
  @Column(name = "FL_EMP_FUNDO_DB")
  private String flEmpFundoDb;
  
  @Column(name = "CD_PESSOA_BUBA")
  private Long cdPessoaBuba;
  
  @Column(name = "FL_TIPO_PESSOA")
  private String flTipoPessoa;
  
  @Column(name = "CD_CGC_CPF")
  private Long cdGcgCpf;
  
  @Column(name = "CD_COSIF")
  private Long cdCosif;
  
  @Column(name = "CD_NACE")
  private Long cdNace;
  
  @Column(name = "DT_INCLUSAO")
  @JsonFormat(pattern = "dd-MM-yy")
  private Date dtInclusao;
  
  @Column(name = "VL_PTRM_LQD")
  private Double vlPtrmLqd;
  
  @Column(name = "VL_PREV_MVT")
  private Double vlPrevMvt;
  
  @Column(name = "FL_SENSIBILIDADE")
  private String flSensibilidade;
  
  @Column(name = "CD_TP_EMPRESA")
  private Long cdTpEmpresa;
  
  @Column(name = "CD_CATG_PESSOA")
  private Long cdCatgPessoa;
  
  @Column(name = "CD_PAIS_ORIGEM")
  private Long codPaisOrigem;
  
  @Column(name = "COD_SIT_PESSOA")
  private String codSitPessoa;
  
  @Column(name = "IND_PEP")
  private String indPep;
  
  @Column(name = "VL_TOT_ATIVOS")
  private Double vlTotAtivos;
  
  @Column(name = "VL_FATURAMENTO")
  private Double vlFaturamento;
  
  @Column(name = "VL_REC_OPERACIONAL")
  private Double vlRecOperacional;
  
  @Column(name = "VL_OUTR_CAP_FINANC")
  private Double vlOutrCapFinan;
  
  @Column(name = "FL_CAP_FINANC")
  private String flCapFinanc;
  
  @Column(name = "CD_PARAGON")
  private String cdParagon;
  
  @Column(name = "FL_BENEF_FINAL")
  private String flBenefFinal;
  
  @Column(name = "CD_CONTRATO_GLOBAL")
  private Long cdContratoGlobal;
  
  @Column(name = "CD_PARAGON_GRUPO")
  private String cdParagonGrupo;
  
  @Column(name = "FL_RATING_PARAGON")
  private String flRatingParagon;
  
  @Column(name = "FL_RATING_GRUPO_PARAGON")
  private String flRatingGrupoParagon;
  
  @Column(name = "FL_RATING_BACEN")
  private String flRatingBacen;
  
  @Column(name = "FL_RATING_GRUPO_BACEN")
  private String flRatingGrupoBacen;

  @Column(name = "DT_RATING_GRUPO_PARAGON")
  @JsonFormat(pattern = "dd-MM-yy")
  private Date dtRatingGrupoParagon;
  
  @Column(name = "DT_RATING_PARAGON")
  @JsonFormat(pattern = "dd-MM-yy")  
  private Date dtRatingParagon;
  
  @Column(name = "FL_RATING_ARRASTO_BACEN")
  private String flRatingArratoBacen;
  
  @Column(name = "FL_RATING_ARRASTO_PARAGON")
  private String flRatingArratoParagon;
  
  @Column(name = "DS_LOGRADOURO")
  private String dsLogradouro;
  
  @Column(name = "DS_CIDADE")
  private String dsCidade;
  
  @Column(name = "CD_UF")
  private String cdUf;
  
  @Column(name = "CD_CEP")
  private Long cdCep;
  
  @Column(name = "DT_UPDATE")
  @JsonFormat(pattern = "dd-MM-yy")
  private Date dtUpdate;
  
  @Column(name = "FL_CGD")
  private String flCgd;
  
  @Column(name = "NR_LOGRADOURO")
  private String nrLogradouro;
  
  @Column(name = "DS_COMPL_LOGR")
  private String dsComplLogr;
  
  @Column(name = "DS_BAIRRO")
  private String dsBairro;
  
  @Column(name = "TELEFONE_PESSOA")
  private String telefonePessoa;
  
  @Column(name = "DATA_ATUALIZACAO_RENDA")
  @JsonFormat(pattern = "dd-MM-yy")
  private Date dataAtualizacaoRenda;
  
  @Column(name = "DATA_FIM_RELACIONAMENTO_CONTA")
  @JsonFormat(pattern = "dd-MM-yy")
  private Date dataFimRelacionamentoConta;
  
  @Column(name = "CD_ASSESSOR")
  private Long cdAssessor;
  
  @Column(name = "NM_ASSESSOR")
  private String nmAssessor;
  
  @Column(name = "EMAIL_ASSESSOR")
  private String emailAssessor;
  
}
