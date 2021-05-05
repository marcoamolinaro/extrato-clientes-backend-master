package com.db.extrato.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.TbdwDmMoe;
import com.db.extrato.dto.TbdwDmMoeDTO;
import com.db.extrato.repository.extract.TbdwDmMoeRepository;

@Service
public class TbdwDmMoeService {

	@Autowired
	private TbdwDmMoeRepository tbdwDmMoeRepository;

	@Autowired
	private ModelMapper modelMapper;

	public TbdwDmMoeDTO find(Long cdMoeda) {
		return modelMapper.map(tbdwDmMoeRepository.findById(cdMoeda).orElseThrow(() -> new EmptyResultDataAccessException(1)), TbdwDmMoeDTO.class);
	}

	public List<TbdwDmMoeDTO> findAll() {
		List<TbdwDmMoe> moedas = tbdwDmMoeRepository.findByDtExclusaoPtaxIsNullOrderByCdMoedaIsoAsc();
		if (CollectionUtils.isEmpty(moedas))
			throw new EmptyResultDataAccessException(1);
		return moedas.stream().map(m -> modelMapper.map(m, TbdwDmMoeDTO.class)).collect(Collectors.toList());
	}

}
