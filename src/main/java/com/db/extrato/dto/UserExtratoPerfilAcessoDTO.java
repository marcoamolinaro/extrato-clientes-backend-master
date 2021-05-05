package com.db.extrato.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserExtratoPerfilAcessoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String username;
	private List<String> acessos;
}
