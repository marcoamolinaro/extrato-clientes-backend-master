package com.db.extrato.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.TbdwDmInx;
import com.db.extrato.dto.TbdwDmInxDTO;
import com.db.extrato.repository.extract.TbdwDmInxRepository;

@Service
public class TbdwDmInxService {

	@Autowired
	private TbdwDmInxRepository tbdwDmInxRepository;

	@Autowired
	private ModelMapper modelMapper;

	public TbdwDmInxDTO find(Long cdIndexador) {
		return modelMapper.map(tbdwDmInxRepository.findById(cdIndexador).orElseThrow(() -> new EmptyResultDataAccessException(1)), TbdwDmInxDTO.class);
	}

	public List<TbdwDmInxDTO> findAll() {
		List<TbdwDmInx> indexadores = tbdwDmInxRepository.findAll();
		if (CollectionUtils.isEmpty(indexadores))
			throw new EmptyResultDataAccessException(1);
		return indexadores.stream().map(m -> modelMapper.map(m, TbdwDmInxDTO.class)).collect(Collectors.toList());
	}

}
