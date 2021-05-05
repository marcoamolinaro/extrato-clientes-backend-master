package com.db.extrato.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientExtractManagementDTO {
	private Boolean isInsertedOnExtract;
	
	private String cdSitPessoa;
	
	private Long cdCliente;

    private String nomeCliente;
	
	private String cpfCnpjCliente;
	
    private String vrAgendamento;
	
	private String vrPeriodo;

    private String vrHorario;

	private String vrDiaSemana;

    private String dtRef;

    private String vrTipoRelatorio;

	private String vrEmailFlag;	 
	
}
