package com.db.extrato.controller.filter;

import lombok.Data;

@Data
public class TbdwDmPessoaFilter {

  private Long cdPessoa;
  
  private String nmPessoa;

  private String nmPessoaAbrev;

  private Long cdGcgCpf;
  
  private String codSitPessoaAtiva;
  
  private String codSitPessoaBloqueada;  
}
