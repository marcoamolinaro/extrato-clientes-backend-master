package com.db.extrato.domain.extract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TBDW_DIM_MODALIDADE", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TbdwDmModalidade implements Serializable{
  

  private static final long serialVersionUID = 3299053958937743764L;

  @Id
  @Column(name = "CD_SEQ_MODALIDADE")
  private Long cdSeqModalidade;
  
  @Column(name = "CD_MODALIDADE")
  private Long cdModalidade;

  @Column(name = "DS_MODALIDADE")
  private String dsModalidade;

  @Column(name = "CD_PRODUTO")
  private Long cdProduto;

  @Column(name = "DS_PRODUTO")
  private String dsProduto;

  @Column(name = "CD_GRPROD")
  private Long cdGrProduto;
  
  @Column(name = "DS_GRPROD")
  private String dsGrProduto;

  @Column(name = "CD_FAMPROD")
  private Long cdFamProduto;

  @Column(name = "DS_FAMPROD")
  private String dsFamProduto;
  
}
