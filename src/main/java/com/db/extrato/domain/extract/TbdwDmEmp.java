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
@Table(name="TBDW_DIM_EMP", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TbdwDmEmp implements Serializable{

  private static final long serialVersionUID = -93354464739257213L;

  @Id
  @Column(name = "CD_EMPRESA")
  private Long cdEmpresa;
  
  @Column(name = "NM_EMPRESA")
  private String nomeEmpresa;
  
  @Column(name = "CD_GRUPO_EMPRESA")
  private String cdGrupoEmpresa;
  
  @Column(name = "NM_GRUPO_EMPRESA")
  private String nmGrupoEmpresa;
  
  @Column(name = "CD_HOLDING_EMPRESA")
  private String cdHoldingEmpresa;
  
  @Column(name = "NM_HOLDING_EMPRESA")
  private String nmHoldingEmpres;
  
  @Column(name = "CD_MOEDA_EMPR")
  private Long cdMoedaEmp;
  
  @Column(name = "CD_TPEMP")
  private String cdTpemp;
  
  @Column(name = "CD_FLUXO_CAIXA")
  private Long cdFLuxoCaixa;
  
  @Column(name = "NM_FLUXO_CAIXA")
  private String nmFluxoCaixa;
  
  @Column(name = "DB_CONS")
  private Long dbCons;
  
  @Column(name = "DMP_CODE")
  private Long dmpCode;
  

}
