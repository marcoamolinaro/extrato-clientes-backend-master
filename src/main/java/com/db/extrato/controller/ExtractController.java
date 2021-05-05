package com.db.extrato.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.ExtractFilter;
import com.db.extrato.dto.ReferenceDate;
import com.db.extrato.dto.TexExtratoDTO;
import com.db.extrato.enums.ReportName;
import com.db.extrato.enums.ReportType;
import com.db.extrato.service.ExtractService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/extract")
public class ExtractController {

	@Autowired
	private ExtractService extractService;

	@ApiResponses(value = { 
					@ApiResponse(responseCode = "200", description = "Relatorio extraido com sucesso."),
					@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
								content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/report/{reportName}/{reportType}")
	public ResponseEntity<byte[]> report(ExtractFilter extractFilter, @PathVariable ReportName reportName, @PathVariable ReportType reportType, Pageable page) throws Exception {

		try {
			byte report[];
 			report = extractService.report(extractFilter, reportName, reportType, page);
			return ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Extrato_"+extractFilter.getCdEmpresa()+"_"+reportName.name()+"_"+reportType.name()+".pdf")
					.contentLength(report.length)
					.contentType(MediaType.APPLICATION_PDF)
					.body(report);
		} catch (ParseException e) {
			throw new Exception("Insira a data referencia no formato AAAA-MM-YY.");
        }

	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Extratos extraidos com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/{reportName}/{reportType}")
	public ResponseEntity<Page<TexExtratoDTO>> findExtract(ExtractFilter extractFilter, @PathVariable ReportName reportName, @PathVariable ReportType reportType, Pageable page) {
				
		
	    Page<TexExtratoDTO> extracts = extractService.findExtractPage(extractFilter, reportName, reportType, page);
		return ResponseEntity.ok(extracts);
	
	}
	
	@GetMapping("/date-reference")
	public ResponseEntity<ReferenceDate> getReferenceDate() {
	  
	  ReferenceDate date = new ReferenceDate();
	  date.setDateTipe("Data Referencia");
	  date.setDate(extractService.getReferenceDate());
	  
	  return ResponseEntity.ok(date);
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Extrato extraido com sucesso."),
			@ApiResponse(responseCode = "404", description = "Extrato não encontrado.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/{cdEmpresa}/{cdCliente}/{cdModalidade}/{cdOperOrigem}")
	public  ResponseEntity<TexExtratoDTO> find(@PathVariable Long cdEmpresa, @PathVariable Long cdCliente, @PathVariable Long cdModalidade, @PathVariable String cdOperOrigem) {
		TexExtratoDTO extract = extractService.findExtract(cdEmpresa, cdCliente, cdModalidade, cdOperOrigem);
		return ResponseEntity.ok(extract);
	}

}
