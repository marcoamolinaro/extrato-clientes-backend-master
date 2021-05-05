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
@Table(name="TBDW_DIM_SIS", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TbdwDmSis implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_SEQ_SIS_ORIG")
	@EqualsAndHashCode.Include
	private Long cdSeqSisOrig;
	
	@Column(name = "CD_SIS_ORIG")
    private String cdSisOrig;
	
	@Column(name = "NM_SISTEMA")
    private String nmSistema;	
}
