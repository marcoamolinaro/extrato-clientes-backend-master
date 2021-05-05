package com.db.extrato.util.secserver;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class PerfilAcessoSecServer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String perfilAcesso;

}
