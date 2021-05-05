package com.db.extrato.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.domain.extract.ClientExtractManagement;
import com.db.extrato.dto.ClientExtractManagementDTO;
import com.db.extrato.dto.ClientExtractManagementResultDTO;
import com.db.extrato.enums.VrPeriodo;
import com.db.extrato.repository.extract.ClientExtractManagementRepository;
import com.db.extrato.service.ClientExtractManagementService;
import com.db.extrato.util.Constants;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/client/management")
public class ClientExtractManagementController {
	
	@Autowired
	private ClientExtractManagementService clientExtratManagementService;
	
	@Autowired
	private ClientExtractManagementRepository clientRepository;
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações recuperadas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/gridderivativo/geracaoextrato")
	public  ResponseEntity<Page<ClientExtractManagementDTO>> getGridDerivativoGeracaoExtrato(
			@RequestParam(value="clientname", defaultValue="") String clientname,
			Pageable page){
		        
	    Page<ClientExtractManagementDTO> clients = clientExtratManagementService.getGridExtratoDerivativo(clientname, page);	    	
				  
		return ResponseEntity.ok(clients);
	}

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações recuperadas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/gridderivativo/manutencaocliente")
	public  ResponseEntity<Page<ClientExtractManagementDTO>> getGridExtratoDerivativoManutencao(
			@RequestParam(value="clientname", defaultValue="") String clientname,
			Pageable page){
		        	    
	    Page<ClientExtractManagementDTO> clients = null;
	    
	    clients = clientExtratManagementService.getGridExtratoDerivativoManutencao(clientname, page);
			  
		return ResponseEntity.ok(clients);
	}
		
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações adicionado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Erro ao adicionar informações.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@PostMapping("/add")
	public ResponseEntity<?> saveAllClientExtractManagements(@Valid @RequestBody List<ClientExtractManagementDTO> dtos) {
		
		List<ClientExtractManagementDTO> dtoInserteds = new ArrayList<ClientExtractManagementDTO>();
		
		List<ClientExtractManagementResultDTO> listaResult = new ArrayList<ClientExtractManagementResultDTO>();
						
		dtos.forEach( d -> {
			
			ClientExtractManagementResultDTO resultDTO = new ClientExtractManagementResultDTO();
			
			List<String> erros = clientExtratManagementService.isValid(d);
			
			if (!erros.isEmpty()) {
				resultDTO.setMsgErros(erros);
			} else {
				try {
					ClientExtractManagement client = clientExtratManagementService.insert(d);
					
					if (client != null) {
						d.setIsInsertedOnExtract(true);
						if (d.getVrPeriodo().equals(Constants.EMPTY) || d.getVrPeriodo().equals(Constants.SPACE_1) || d.getVrPeriodo() == null) {
							d.setVrPeriodo(Constants.NENHUM);
						}
						dtoInserteds.add(d);
						resultDTO.addClientDtos(d);
						if (!d.getVrPeriodo().equals(Constants.NENHUM)) {
							ClientExtractManagement client1 = clientExtratManagementService.getById(d.getCdCliente(), Constants.NENHUM);
							if (client1 != null) {
								clientRepository.delete(client1);
							}
						}
					}
				} catch (Exception e) {
					log.info("Erro ao inserir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
					System.out.println("Erro ao inserir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
					resultDTO.addErros("Erro ao inserir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
				}				
			}
			listaResult.add(resultDTO);	
			
		});
			
		return ResponseEntity.ok().body(listaResult);
	}
	
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações excluídas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Erro ao excluir informações.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteClientExtractManagements(@Valid @RequestBody List<ClientExtractManagementDTO> dtos) {
				
		List<ClientExtractManagementDTO> dtoDeleteds = new ArrayList<ClientExtractManagementDTO>();
		
		dtos.forEach( d -> {
			
			try {
				Boolean deleted = clientExtratManagementService.delete(d);
				if (deleted) {

					d.setIsInsertedOnExtract(false);
					
					if (d.getVrPeriodo().equals(Constants.EMPTY) || d.getVrPeriodo().equals(Constants.SPACE_1) || d.getVrPeriodo() == null) {
						d.setVrPeriodo(VrPeriodo.getVrPeriodoByString(Constants.NENHUM).getPeriod());
					}
					dtoDeleteds.add(d);
				}
			} catch (Exception e) {
				log.info("Erro ao excluir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
				System.out.println("Erro ao excluir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
			}
			
		});
		
		dtos.forEach( d -> {
			List<ClientExtractManagement> clientList = clientRepository.findByClientExtractManagementIdCdCliBr(d.getCdCliente());
			
			if (clientList == null || clientList.isEmpty()) {
				d.setVrPeriodo(Constants.NENHUM);
				d.setDtRef(Constants.SPACE_1);
				d.setIsInsertedOnExtract(true);
				d.setVrAgendamento(Constants.ZERO);
				d.setVrDiaSemana(Constants.SPACE_1);
				d.setVrEmailFlag(Constants.SPACE_1);
				d.setVrHorario(Constants.SPACE_1);
				d.setVrTipoRelatorio(Constants.SPACE_1);
				clientExtratManagementService.insert(d);
			}
		});
		
		return ResponseEntity.ok().body(dtoDeleteds);
	}	
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações excluídas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Erro ao excluir informções.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@DeleteMapping("/deleteall")
	public ResponseEntity<?> deleteAllClientExtractManagements(@Valid @RequestBody List<ClientExtractManagementDTO> dtos) {
				
		List<ClientExtractManagementDTO> dtoDeleteds = new ArrayList<ClientExtractManagementDTO>();
		
		dtos.forEach( d -> {
			
			try {
				Boolean deleted = clientExtratManagementService.deleteAll(d);
				if (deleted) {

					d.setIsInsertedOnExtract(false);
					
					if (d.getVrPeriodo().equals(Constants.EMPTY) || d.getVrPeriodo().equals(Constants.SPACE_1) || d.getVrPeriodo() == null) {
						d.setVrPeriodo(VrPeriodo.getVrPeriodoByString(Constants.NENHUM).getPeriod());
					}
					dtoDeleteds.add(d);
				}
			} catch (Exception e) {
				log.info("Erro ao excluir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
				System.out.println("Erro ao excluir cliente "+ d.getCdCliente() + " - " + d.getNomeCliente());
			}
			
		});
		
		return ResponseEntity.ok().body(dtoDeleteds);
	}	
}
