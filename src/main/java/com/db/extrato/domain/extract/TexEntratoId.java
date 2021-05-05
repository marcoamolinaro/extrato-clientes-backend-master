package com.db.extrato.domain.extract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString(includeFieldNames=true)
public class TexEntratoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CD_EMPRESA")
	@NotBlank(message="Cod Empresa is required")
	private Long cdEmpresa;
	
	@Column(name = "CD_CLIENTE")
	@NotBlank(message="Cod Cliente is required")
	private Long cdCliente;
	
	@Column(name = "CD_MODALIDADE")
	@NotBlank(message="Cod Modalidade is required")
	private Long cdModalidade;
	
	@Column(name = "CD_OPER_ORIGEM")
	@NotBlank(message="Cod Oper Origem is required")
	private String cdOperOrigem;
}
