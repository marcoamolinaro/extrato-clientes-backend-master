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
@Table(name="TBDW_DIM_INX", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TbdwDmInx implements Serializable {
	
	private static final long serialVersionUID = -6851156458097568571L;

	@Id
	@Column(name = "CD_INDEXADOR")
	@EqualsAndHashCode.Include
	private Long cdIndexador;
	
	@Column(name = "NM_INDEXADOR")
    private String nmIndexador;
	
	@Column(name = "FL_TIPO_INX")
    private String flTipoInx;
	
	@Column(name = "CD_MOEDA_DE")
	private Long cdMoedaDe;
	
	@Column(name = "CD_MOEDA_PARA")
	private Long cdMoedaPara;
	
	@Column(name = "FL_TIPO_PARIDADE")
	private String flTipoParidade;
	
	@Column(name = "CD_TPPE")
	private String cdTppe;
	
	@Column(name = "FL_TIPO_PARIDADE_MTM")
	private String flTipoParidadeMtm;
	
	@Column(name = "CD_INX_CERI")
	private Long cdInxCeri;
	
}
