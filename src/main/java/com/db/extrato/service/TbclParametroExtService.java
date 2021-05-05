package com.db.extrato.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.TbclParametroExt;
import com.db.extrato.dto.TbclParametroExtDTO;
import com.db.extrato.repository.extract.TbclParametroExtRepository;

@Service
public class TbclParametroExtService {

  @Autowired
  private TbclParametroExtRepository extRepository;
  
  public TbclParametroExtDTO updtate(Long id, TbclParametroExt tbclParametroExt) {
    
     TbclParametroExt parametroDto =  extRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    
     BeanUtils.copyProperties(tbclParametroExt, parametroDto, "cdSeqModalidade");
     
     return TbclParametroExtDTO.convertDto(extRepository.save(parametroDto));
     
  }
  
}
