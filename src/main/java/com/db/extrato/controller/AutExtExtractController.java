package com.db.extrato.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.AutExtExtractFilter;
import com.db.extrato.dto.AutExtTexExtratoDTO;
import com.db.extrato.service.AutExtExtractService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/extract/aut")
public class AutExtExtractController {

	@Autowired
	private AutExtExtractService autExtExtractService;

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitação para adicionar extrato realizada com sucesso."),
			@ApiResponse(responseCode = "406", description = "Já existe uma solicitação pendente para este extrato.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PostMapping("/add")
	public ResponseEntity<AutExtTexExtratoDTO> insertAutExtExtract(Authentication authentication, @Valid @RequestBody AutExtTexExtratoDTO autExtTexExtratoDTO) {
		return ResponseEntity.ok().body(autExtExtractService.insert(authentication.getName(), autExtTexExtratoDTO));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitação para alterar extrato realizada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "406", description = "Já existe uma solicitação pendente para este extrato.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PutMapping("/update")
	public ResponseEntity<AutExtTexExtratoDTO> updateAutExtExtract(
			Authentication authentication, 
			@RequestParam Long cdEmpresa, 
			@RequestParam Long cdCliente, 
			@RequestParam Long cdModalidade, 
			@RequestParam String cdOperOrigem, 
			@Valid @RequestBody AutExtTexExtratoDTO autExtTexExtratoDTO) {
		return ResponseEntity.ok().body(autExtExtractService.update(cdEmpresa, cdCliente, cdModalidade, cdOperOrigem, authentication.getName(), autExtTexExtratoDTO));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitação para excluir extrato realizada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "406", description = "Já existe uma solicitação pendente para este extrato.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@DeleteMapping("/delete")
	public ResponseEntity<AutExtTexExtratoDTO> deleteAutExtExtract(
			Authentication authentication, 
			@RequestParam Long cdEmpresa, 
			@RequestParam Long cdCliente, 
			@RequestParam Long cdModalidade, 
			@RequestParam String cdOperOrigem) {
		return ResponseEntity.ok().body(autExtExtractService.delete(cdEmpresa, cdCliente, cdModalidade, cdOperOrigem, authentication.getName()));
	}

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitações para alteração de extratos extraidas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping
	public ResponseEntity<Page<AutExtTexExtratoDTO>> findAutExtExtractPage(AutExtExtractFilter autExtExtractFilter, Pageable page) {
		return ResponseEntity.ok(autExtExtractService.findPage(autExtExtractFilter, page));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitação para alteração de extratos extraida com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/{autCdSequencial}")
	public ResponseEntity<AutExtTexExtratoDTO> findAutExtExtract(@PathVariable Long autCdSequencial) {
		return ResponseEntity.ok(autExtExtractService.find(autCdSequencial));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitação para alterar extrato rejeitada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "406", description = "Solicitação não está pendente.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PostMapping("/{autCdSequencial}/rejeitar")
	public ResponseEntity<AutExtTexExtratoDTO> rejeitarAutExtExtract(Authentication authentication, @PathVariable Long autCdSequencial) {
		return ResponseEntity.ok().body(autExtExtractService.rejeitar(autCdSequencial, authentication.getName()));
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Solicitação para alterar extrato aprovada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class))),
			@ApiResponse(responseCode = "406", description = "Solicitação não está pendente.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PostMapping("/{autCdSequencial}/aprovar")
	public ResponseEntity<AutExtTexExtratoDTO> aprovarAutExtExtract(Authentication authentication, @PathVariable Long autCdSequencial) {
		return ResponseEntity.ok().body(autExtExtractService.aprovar(autCdSequencial, authentication.getName()));
	}
	
}
