package com.db.extrato.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.extrato.domain.extract.TbdwDmEmp;
import com.db.extrato.repository.extract.TbdwDmEmpRepository;

@RestController
@RequestMapping("/company")
public class TbdwDmEmpController {

  @Autowired
  private TbdwDmEmpRepository companyRepository;
  
  @GetMapping
  public List<TbdwDmEmp> find(String query){
    
    List<TbdwDmEmp> list = new ArrayList<>();
    
    if(StringUtils.hasText(query)) {
       list = companyRepository.findByName(query);
    }else {
      list = companyRepository.findByOrderByNomeEmpresaAsc();
    }
    
    return list;
  }
  
}
