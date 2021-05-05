package com.db.extrato.domain.extract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TBCL_PARAMETRO_EXT", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
@SequenceGenerator(name="SEQ_TBCL_PARAMETRO_EXT", allocationSize=1)
public class TbclParametroExt implements Serializable{

  private static final long serialVersionUID = -7718910097111570328L;
  public static final String CONSTANT_TRRO = "TRRO";
  public static final String CONSTANT_CAMD = "CAMD";
  public static final String CONSTANT_CAEX = "CAEX";
  public static final String CONSTANT_SNOP = "SNOP";
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBCL_PARAMETRO_EXT")
  @Column(name = "SEQ_PARAMETRO")
  Long cdSeqModalidade;
  
  @NotNull(message = "Preencha o Campo Código da Modalidade.")
  @Column(name = "CD_MODALIDADE")
  private Long cdModalidade;
  
  @NotNull(message = "Preencha o código do sistema de origem.")
  @Column(name = "CD_SIS_ORIG")
  private String cdSisOrig;
  
  @Column(name = "QT_CASA_DEC")
  private int qtCasaDec;

  @Column(name = "ID_D_TRRO")
  private String idDTrro = CONSTANT_TRRO;

  @NotNull(message = "Pree ncha o Campo Trunca/Arredonda.")
  @Column(name = "ID_DD_TRRO")
  private String iddDTrro;
  
  @Column(name = "ID_D_CAMD")
  private String idDCamd = CONSTANT_CAMD;
  
  @NotNull(message = "Preencha o Campo de Origem(Midas).")
  @Column(name = "ID_DD_CAMD")
  private String iddDCamd;

  @Column(name = "ID_D_CAEX")
  private String idDCaex = CONSTANT_CAEX;

  @NotNull(message = "Preencha o Campo de Destino(Extrato).")
  @Column(name = "ID_DD_CAEX")
  private String iddDCaex;

  @Column(name = "ID_D_SNOP")
  private String idDSnop = CONSTANT_SNOP;
  
  @NotNull(message = "Preencha o valor Absoluto.")
  @Column(name = "ID_DD_SNOP")
  private String iddDSnop;

  @NotNull(message = "Preencha do código do produto.")
  @Column(name = "CD_PRODUTO")
  private Long cdProduto;

  @NotNull(message = "Preencha o código da moeda da operação.")
  @Column(name = "CD_MOEDA_OPER")
  private Long cdMoedaOper;

  
}
