package com.db.extrato.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.DerivativesFilter;
import com.db.extrato.service.DerivativesService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/derivatives")
public class DerivativesController {

	@Autowired
	private DerivativesService derivativesService;
	
	@GetMapping("/pdf")
	public ResponseEntity<byte[]> derivativesReportPdf(Authentication authentication, @RequestParam(name = "data", required = true) String data, @RequestParam(name = "cdCliente", required = true) Long cdCliente) throws JRException, ParseException {
		Date dateAtual = new Date();
		Map<String, byte[]> report = derivativesService.report(authentication.getName(), false, new SimpleDateFormat("yyyy-MM-dd").parse(data), dateAtual, cdCliente, true, false, false);
		Entry<String, byte[]> reportEntry = report.entrySet().iterator().next();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportEntry.getKey())
				.contentLength(reportEntry.getValue().length)
				.contentType(MediaType.APPLICATION_PDF)
				.body(reportEntry.getValue());
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Extrato gerado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não existem dados para essa data e cliente(s).",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/zip")
	public ResponseEntity<byte[]> derivativesZip(Authentication authentication, DerivativesFilter derivativesFilter, @RequestParam(name = "pdf", required = true) boolean pdf, @RequestParam(name = "excel", required = true) boolean excel) throws JRException, IOException, ParseException {
		Date dateAtual = new Date();
		Date data = new SimpleDateFormat("yyyy-MM-dd").parse(derivativesFilter.getData());
		byte[] zip = derivativesService.reportZip(authentication.getName(), false, data, new Date(), derivativesFilter.getCdClientes(), pdf, excel);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new SimpleDateFormat("yyyyMMdd").format(data) + "-" + DateFormat.getTimeInstance(DateFormat.MEDIUM).format(dateAtual) + "_Extract_Report.zip")
				.contentLength(zip.length)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(zip);
	}
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Extrato gerado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não existem dados para essa data e cliente(s)",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/email")
	public ResponseEntity<Void> derivativesEmail(Authentication authentication, DerivativesFilter derivativesFilter) throws IOException, JRException, ParseException {
		Date data = new SimpleDateFormat("yyyy-MM-dd").parse(derivativesFilter.getData());
		derivativesService.reportEmail(authentication.getName(), false, data, new Date(), derivativesFilter.getCdClientes());
		return ResponseEntity.ok().build();
	}
	
}
