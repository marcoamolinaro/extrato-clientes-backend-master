package com.db.extrato.domain.extract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="CLIENT_EXTRACT_MANAGEMENT", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class ClientExtractManagement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	private ClientExtractManagementId clientExtractManagementId;
	
	@Column(name = "NM_CLI")
	private String nmCli;

	@Column(name = "VR_AGENDAMENTO")
	private String vrAgendamento;
	
	@Column(name = "VR_HORARIO")
	private String vrHorario;
	
	@Column(name = "VR_DIA_SEMANA")
	private String vrDiaSemana;
	
	@Column(name = "VR_TIPO_RELATORIO")
	private String vrReportType;
	
	@Column(name = "DT_REF")
	private String dtRef;
	
	@Column(name = "VR_EMAIL_FLAG")
	private String vrEmailFlag;	
}

