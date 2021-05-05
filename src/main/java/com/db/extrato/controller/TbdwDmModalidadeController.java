package com.db.extrato.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.domain.extract.TbdwDmModalidade;
import com.db.extrato.dto.ProdDTO;
import com.db.extrato.dto.TbdwDmModalidadeDTO;
import com.db.extrato.repository.extract.TbdwDmModalidadeRepository;
import com.db.extrato.service.TbdwDmModalidadeService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/modality")
public class TbdwDmModalidadeController {

  @Autowired
  private TbdwDmModalidadeRepository modalityRepository;
  
  @Autowired
  private TbdwDmModalidadeService tbdwDmModalidadeService;
  
  @GetMapping
  public List<TbdwDmModalidade> find(String query){
    
    List<TbdwDmModalidade> list = new ArrayList<>();
    
    if(StringUtils.hasText(query)) {
       list = modalityRepository.findByCdModalidadeOrDsModalidade(query);
    }else {
      list = modalityRepository.findByOrderByDsModalidadeAsc();
    }
    
    return list;
  }
  
  @ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Produto extraido com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
  @GetMapping("/prod/{cdProduto}")
  public ResponseEntity<ProdDTO> findProd(@PathVariable Long cdProduto) {
	  return ResponseEntity.ok().body(tbdwDmModalidadeService.findProd(cdProduto));
  }
  
  @ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Produtos extraidos com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
  @GetMapping("/prod")
  public ResponseEntity<List<ProdDTO>> findAllProd() {
	  return ResponseEntity.ok().body(tbdwDmModalidadeService.findAllProd());
  }

  @ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Modalidades extraidas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
  @GetMapping("/{cdProduto}")
  public ResponseEntity<List<TbdwDmModalidadeDTO>> findAllByProduto(@PathVariable Long cdProduto) {
	  return ResponseEntity.ok().body(tbdwDmModalidadeService.findAllByProduto(cdProduto));
  }
  
}
