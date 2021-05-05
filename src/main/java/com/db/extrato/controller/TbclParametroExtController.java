package com.db.extrato.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.TbclParametroExtFilter;
import com.db.extrato.domain.extract.TbclParametroExt;
import com.db.extrato.dto.TbclParametroExtDTO;
import com.db.extrato.repository.extract.TbclParametroExtRepository;
import com.db.extrato.repository.extract.impl.TbclParametroExtRepositoryImpl;
import com.db.extrato.service.TbclParametroExtService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/parameterModality")
public class TbclParametroExtController {

  @Autowired
  private TbclParametroExtRepositoryImpl extRepositoryImpl;
  
  @Autowired
  private TbclParametroExtRepository extRepository;
  
  @Autowired
  private TbclParametroExtService tbclParametroService;
  
  @ApiResponses(value = { 
      @ApiResponse(responseCode = "404", description = "Não há informações para esta pesquisa.",
                  content = @Content(schema = @Schema(implementation = Void.class)))})
  @GetMapping
  public ResponseEntity<Page<TbclParametroExtDTO>> findAll(
                @RequestParam(value="cdModalidade", required=false) Long cdModalidade,
                @RequestParam(value="cdSisOrig", required=false) String cdSisOrig,
                @RequestParam(value="iddDCamd", required=false) String iddDCamd,
                @RequestParam(value="iddDCaex", required=false) String iddDCaex,
                Pageable page){
      TbclParametroExtFilter filter = new TbclParametroExtFilter();
      filter.setCdModalidade(cdModalidade);
      filter.setCdSisOrig(cdSisOrig);
      filter.setIddDCamd(iddDCamd);
      filter.setIddDCaex(iddDCaex);
    return  ResponseEntity.ok().body(extRepositoryImpl.pesquisarPage(filter, page));
  }
  
  @ApiResponses(value = { 
      @ApiResponse(responseCode = "404", description = "Erro ao remover informações.",
      content = @Content(schema = @Schema(implementation = Void.class)))})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    extRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<TbclParametroExtDTO> update(@PathVariable Long id, @Valid @RequestBody TbclParametroExt tbclParametroExt){
    
    TbclParametroExtDTO parametroExtDto = tbclParametroService.updtate(id, tbclParametroExt);
    
    return ResponseEntity.ok(parametroExtDto);
  }
  
  @PostMapping
  public ResponseEntity<TbclParametroExtDTO> insert(@Valid @RequestBody TbclParametroExt tbclParametroExt){
    
    TbclParametroExt parametroExt = extRepository.save(tbclParametroExt);
    
    return ResponseEntity.ok(TbclParametroExtDTO.convertDto(parametroExt));
  }
}
