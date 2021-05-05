package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbdwDmInxDTO {
	
	private Long cdIndexador;
	
    private String nmIndexador;
	
    private String flTipoInx;
	
	private Long cdMoedaDe;
	
	private Long cdMoedaPara;
	
	private String flTipoParidade;
	
	private String cdTppe;
	
	private String flTipoParidadeMtm;
	
	private Long cdInxCeri;
	
}
