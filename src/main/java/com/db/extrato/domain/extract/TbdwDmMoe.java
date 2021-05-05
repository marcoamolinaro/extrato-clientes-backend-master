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
@Table(name = "TBDW_DIM_MOE", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames = true)
public class TbdwDmMoe implements Serializable {

	private static final long serialVersionUID = -8453818668077682275L;

	@Id
	@Column(name = "CD_MOEDA")
	private Long cdMoeda;

	@Column(name = "CD_MOEDA_ISO")
	private String cdMoedaIso;

	@Column(name = "NM_MOEDA")
	private String nmMoeda;

	@Column(name = "TP_PARIDADE_DOLAR")
	private String tpParidadeDolar;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "DT_EXCLUSAO_PTAX")
	private Date dtExclusaoPtax;

}
