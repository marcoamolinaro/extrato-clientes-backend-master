package com.db.extrato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.dto.TbdwDmMoeDTO;
import com.db.extrato.service.TbdwDmMoeService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/currency")
public class TbdwDmMoeController {

	@Autowired
	private TbdwDmMoeService tbdwDmMoeService;

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Moeda extraida com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/{cdMoeda}")
	public ResponseEntity<TbdwDmMoeDTO> findCurrency(@PathVariable Long cdMoeda) {
		return ResponseEntity.ok().body(tbdwDmMoeService.find(cdMoeda));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Moedas extraidas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping
	public ResponseEntity<List<TbdwDmMoeDTO>> findAllActiveCurrency() {
		return ResponseEntity.ok().body(tbdwDmMoeService.findAll());
	}
	
}
