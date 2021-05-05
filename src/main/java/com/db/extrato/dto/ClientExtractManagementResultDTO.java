package com.db.extrato.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClientExtractManagementResultDTO {
	private List<String> msgErros = new ArrayList<String>();
	private List<ClientExtractManagementDTO> clientDTOs = new ArrayList<ClientExtractManagementDTO>();
	public List<String> getMsgErros() {
		return msgErros;
	}
	
	public void addErros(String erro) {
		this.msgErros.add(erro);
	}
	
	public void addClientDtos(ClientExtractManagementDTO dto) {
		clientDTOs.add(dto);
	}
}
