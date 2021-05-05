package com.db.extrato.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.ExtractLogReportFilter;
import com.db.extrato.dto.ExtractLogReportDTO;
import com.db.extrato.repository.extract.impl.ExtractLogReportRepositoryImpl;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/extrato")
public class ExtractLogReportController {

	@Autowired
	private ExtractLogReportRepositoryImpl repositoryImpl;
  	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Informações recuperadas com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
						content = @Content(schema = @Schema(implementation = Void.class)))})
	@GetMapping("/logderivativos")
	public  ResponseEntity<Page<ExtractLogReportDTO>> find(
			@RequestParam(name = "date", required = false) String date, 
			Pageable page) throws Exception, ObjectNotFoundException {
		
		ExtractLogReportFilter filter = new ExtractLogReportFilter();
		
		if (date != null) {
		    SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
		    Date date1=formatter1.parse(date);
	    
		    filter.setDtLog(date1);			
		}
		
		var dtos = repositoryImpl.pesquisarPage(filter, page);
		
		if (dtos.isEmpty()) {
			throw new ObjectNotFoundException("Não há informações para esta pesquisa.");
		}
					    
		return ResponseEntity.ok(dtos);
	}

}
