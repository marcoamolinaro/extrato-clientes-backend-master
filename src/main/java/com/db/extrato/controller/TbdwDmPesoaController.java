package com.db.extrato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.controller.filter.TbdwDmPessoaFilter;
import com.db.extrato.dto.TbdwDmPessoaDTO;
import com.db.extrato.repository.extract.impl.TbdwDmPessoaRepositoryImpl;

@RestController
@RequestMapping("/pessoa")
public class TbdwDmPesoaController {

  @Autowired
  private TbdwDmPessoaRepositoryImpl dmPessoaRepositoryImpl;
  
  @GetMapping
  public  ResponseEntity<Page<TbdwDmPessoaDTO>> findExtract(TbdwDmPessoaFilter tbdwDmPessoaFilter, Pageable page){
    
    return ResponseEntity.ok(dmPessoaRepositoryImpl.pesquisarPage(tbdwDmPessoaFilter, page));

  }
}
