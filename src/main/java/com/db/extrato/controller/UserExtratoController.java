package com.db.extrato.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.dto.UserExtratoPerfilAcessoDTO;
import com.db.extrato.exception.GenericException;
import com.db.extrato.util.secserver.PerfilAcessoSecServer;
import com.db.extrato.util.secserver.SecServerExtrato;
import com.db.extrato.util.secserver.UserExtratoSecServer;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/perfil")
public class UserExtratoController {
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Perfil de Origem extraidos com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/{username}")
	public ResponseEntity<UserExtratoPerfilAcessoDTO> find(@PathVariable String username, Principal principal) {
		
		if (!principal.getName().equals(username)) {
			throw new GenericException("Usuário " + username + " não está logado.");
		}
				
		UserExtratoSecServer uess = SecServerExtrato.getUserExtrato(username); 
		
		if (uess == null) {
			throw new GenericException("Usuário " + username + " não encontrado. Não há informações para esta pesquisa.");
		}
		
		UserExtratoPerfilAcessoDTO dto = new UserExtratoPerfilAcessoDTO();

		List <String> acessos = new ArrayList<String>();
		
		dto.setUsername(uess.getUsername());
		
		List<PerfilAcessoSecServer> perfis = uess.getPerfis();
		
		perfis.forEach(p -> {
			acessos.add(p.getPerfilAcesso());
			log.info("Perfil [" + p.getPerfilAcesso() + "]");
		});
		
		dto.setAcessos(acessos);
		
		return ResponseEntity.ok().body(dto);
	}
	
}
