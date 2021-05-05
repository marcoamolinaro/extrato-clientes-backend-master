package com.db.extrato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.dto.TbdwDmInxDTO;
import com.db.extrato.service.TbdwDmInxService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/indexer")
public class TbdwDmInxController {

	@Autowired
	private TbdwDmInxService tbdwDmInxService;

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Indexador extraido com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/{cdIndexador}")
	public ResponseEntity<TbdwDmInxDTO> findIndexer(@PathVariable Long cdIndexador) {
		return ResponseEntity.ok().body(tbdwDmInxService.find(cdIndexador));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Indexadores extraidos com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping
	public ResponseEntity<List<TbdwDmInxDTO>> findAllIndexers() {
		return ResponseEntity.ok().body(tbdwDmInxService.findAll());
	}
	
}
