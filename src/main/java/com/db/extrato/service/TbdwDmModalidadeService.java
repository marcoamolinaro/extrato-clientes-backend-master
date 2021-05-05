package com.db.extrato.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.TbdwDmModalidade;
import com.db.extrato.dto.ProdDTO;
import com.db.extrato.dto.TbdwDmModalidadeDTO;
import com.db.extrato.repository.extract.TbdwDmModalidadeRepository;

@Service
public class TbdwDmModalidadeService {

	@Autowired
	private TbdwDmModalidadeRepository tbdwDmModalidadeRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public ProdDTO findProd(Long cdProduto) {
		return tbdwDmModalidadeRepository.findProdById(cdProduto).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	public List<ProdDTO> findAllProd() {
		List<ProdDTO> produtos = tbdwDmModalidadeRepository.findAllProdOrderBydsProduto();
		if (CollectionUtils.isEmpty(produtos))
			throw new EmptyResultDataAccessException(1);
		return produtos;
	}
	
	public List<TbdwDmModalidadeDTO> findAllByProduto(Long cdProduto) {
		List<TbdwDmModalidade> modalidades = tbdwDmModalidadeRepository.findAllByCdProdutoOrderByDsModalidade(cdProduto);
		if (CollectionUtils.isEmpty(modalidades))
			throw new EmptyResultDataAccessException(1);
		return modalidades.stream().map(m -> modelMapper.map(m, TbdwDmModalidadeDTO.class)).collect(Collectors.toList());
	}

}
