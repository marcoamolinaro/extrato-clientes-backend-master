package com.db.extrato.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.db.extrato.domain.extract.TblModAplic;

import lombok.Data;

@Data
public class TblModAplicDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long cdSeqModAplic;
	
	private Long cdApplication;
	
	private Long cdModalidade;
	
	private String modalidade;
	
	private String dsNegocio;
	
	private String dsNegocioIngles;
	
	private String displayQtde;
	
	private String displayMe;
	
	private String displayMn;

	@Length(max = 1, message = "Campo deve ter tamanho 1 caracter")
	private String iiTipoContabil;
	
	private String dsObn;
	
	private String cdSisOrig;
	
	private String cgSistema;

	private String cgModulo;
	
	private String nmIndexador;
	
	private String cdGarantia;
	
	public static TblModAplicDTO convertDto(TblModAplic modaplic) {
		TblModAplicDTO modAplicDto = new TblModAplicDTO();
		  
		modAplicDto.setCdSeqModAplic(modaplic.getCdSeqModAplic());
		modAplicDto.setCdApplication(modaplic.getCdApplication());
		modAplicDto.setCdGarantia(modaplic.getCdGarantia());
		modAplicDto.setCdModalidade(modaplic.getCdModalidade());
		modAplicDto.setCgModulo(modaplic.getCgModulo());
		modAplicDto.setCdSisOrig(modaplic.getCdSisOrig());
		modAplicDto.setCgSistema(modaplic.getCgSistema());
		modAplicDto.setDisplayMe(modaplic.getDisplayMe());
		modAplicDto.setDisplayMn(modaplic.getDisplayMn());
		modAplicDto.setDisplayQtde(modaplic.getDisplayQtde());
		modAplicDto.setDsNegocio(modaplic.getDsNegocio());
		modAplicDto.setDsNegocioIngles(modaplic.getDsNegocioIngles());
		modAplicDto.setDsObn(modaplic.getDsObn());
		modAplicDto.setIiTipoContabil(modaplic.getIiTipoContabil());
		modAplicDto.setNmIndexador(modaplic.getNmIndexador());
		    
	    return modAplicDto;
	}
}
