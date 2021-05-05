package com.db.extrato.domain.extract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
public class ClientExtractManagementId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CD_CLI_BR")
	private Long cdCliBr;
	
	@Column(name = "VR_PERIODO")
	private String vrPeriodo;
}
