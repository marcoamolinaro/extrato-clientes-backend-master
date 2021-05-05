package com.db.extrato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.dto.TbdwDmSisDTO;
import com.db.extrato.service.TbdwDmSisService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/sistemaogirem")
public class TbdwDmSisController {

	@Autowired
	private TbdwDmSisService service;
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Sistema de Origem extraidos com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/all")
	public ResponseEntity<List<TbdwDmSisDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
}
