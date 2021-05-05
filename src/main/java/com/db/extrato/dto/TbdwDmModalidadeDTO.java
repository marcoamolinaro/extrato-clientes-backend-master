package com.db.extrato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbdwDmModalidadeDTO {
  
  Long cdSeqModalidade;
  
  private Long cdModalidade;

  private String dsModalidade;

  private Long cdProduto;

  private String dsProduto;

  private Long cdGrProduto;
  
  private String dsGrProduto;

  private Long cdFamProduto;

  private String dsFamProduto;
  
}
