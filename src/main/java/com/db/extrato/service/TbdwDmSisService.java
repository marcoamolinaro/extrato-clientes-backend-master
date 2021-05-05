package com.db.extrato.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.TbdwDmSis;
import com.db.extrato.dto.TbdwDmSisDTO;
import com.db.extrato.repository.extract.TbdwDmSisRepository;

@Service
public class TbdwDmSisService {

	@Autowired
	private TbdwDmSisRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	public List<TbdwDmSisDTO> findAll() {
		List<TbdwDmSis> sisorigens = repository.findAll();
		if (CollectionUtils.isEmpty(sisorigens))
			throw new EmptyResultDataAccessException(1);
		return sisorigens.stream().map(m -> modelMapper.map(m, TbdwDmSisDTO.class)).collect(Collectors.toList());
	}

}
