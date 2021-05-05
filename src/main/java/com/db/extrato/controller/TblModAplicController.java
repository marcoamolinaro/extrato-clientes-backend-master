package com.db.extrato.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.TblModAplicFilter;
import com.db.extrato.domain.extract.TblModAplic;
import com.db.extrato.dto.TblModAplicDTO;
import com.db.extrato.repository.extract.impl.TblModAplicRepositoryImpl;
import com.db.extrato.service.TblModAplicService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/extrato/pre/modalidade")
public class TblModAplicController {

  @Autowired
  private TblModAplicRepositoryImpl repositoryImpl;
  
  @Autowired
  private TblModAplicService service;
  
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações recuperadas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/grid")
	public ResponseEntity<Page<TblModAplicDTO>> find(TblModAplicFilter tblModAplicFilter, Pageable page){
		
		return ResponseEntity.ok(repositoryImpl.pesquisarPage(tblModAplicFilter, page));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Informações adicionadas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Erro ao adicionar informações.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PostMapping("/add")
	public ResponseEntity<TblModAplic> inserir(@Valid @RequestBody TblModAplicDTO dto) {
		TblModAplic obj = service.insert(dto);
		
		return ResponseEntity.ok(obj);	 
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "404", description = "Erro ao excluir informações.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) throws ObjectNotFoundException {
		service.excluir(id);
		 
		return ResponseEntity.ok().build();
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações atualizadas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Erro ao atualizar informações.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PutMapping("/update/{id}")
	public ResponseEntity<Void> alterar(@Valid @RequestBody TblModAplicDTO dto,  @PathVariable Long id) throws ObjectNotFoundException {
		service.alterar(dto, id);
		 
		return ResponseEntity.ok().build();
	}
}
