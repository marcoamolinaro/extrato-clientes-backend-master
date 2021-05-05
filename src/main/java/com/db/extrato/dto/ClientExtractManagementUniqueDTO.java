package com.db.extrato.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientExtractManagementUniqueDTO {
	private Long cdCliBr;

    private String nmCli;
		
    private String vrAgendamento;
}
