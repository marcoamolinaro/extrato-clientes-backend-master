package com.db.extrato.service;

import java.util.Date;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.extrato.controller.filter.AutExtExtractFilter;
import com.db.extrato.domain.extract.AutExtTexExtrato;
import com.db.extrato.domain.extract.TexEntratoId;
import com.db.extrato.domain.extract.TexExtrato;
import com.db.extrato.dto.AutExtTexExtratoDTO;
import com.db.extrato.enums.AutDsTipoOperacao;
import com.db.extrato.enums.AutStatus;
import com.db.extrato.exception.AutStatusException;
import com.db.extrato.repository.extract.AutExtTexExtratoRepository;
import com.db.extrato.repository.extract.TexExtratoRepository;
import com.db.extrato.repository.extract.impl.AutExtTexExtratoRepositoryImpl;
import com.db.extrato.repository.extract.impl.ExtractRepositoryImpl;

@Service
public class AutExtExtractService {

	@Autowired
	private TexExtratoRepository texExtratoRepository;

	@Autowired
	private AutExtTexExtratoRepository autExtTexExtratoRepository;

	@Autowired
	private ExtractRepositoryImpl extractRepositoryImpl;
	
	@Autowired
	private AutExtTexExtratoRepositoryImpl autExtTexExtratoRepositoryImpl;
  
	@Autowired
	private ModelMapper modelMapper;
	
	public AutExtTexExtratoDTO insert(String autCdUsuarioSolicitacao, AutExtTexExtratoDTO dto) {
		AutExtTexExtrato entity = modelMapper.map(dto, AutExtTexExtrato.class);
		if(autExtTexExtratoRepository.existsByCdEmpresaAndCdClienteAndCdModalidadeAndCdOperOrigemAndAutStatus(entity.getCdEmpresa(), entity.getCdCliente(), entity.getCdModalidade(), entity.getCdOperOrigem(), AutStatus.PENDENTE))
			throw new AutStatusException("Já existe uma solicitação pendente para este extrato.");
		entity.setAutCdUsuarioSolicitacao(autCdUsuarioSolicitacao);
		entity.setAutDsTipoOperacao(AutDsTipoOperacao.INSERT);
		entity.setAutStatus(AutStatus.PENDENTE);
		entity.setAutDtSolicitacao(new Date());
		entity.setDtReferencia(extractRepositoryImpl.getRefereceDate());
		return modelMapper.map(autExtTexExtratoRepository.save(entity), AutExtTexExtratoDTO.class);
	}
	
	public AutExtTexExtratoDTO update(Long cdEmpresa, Long cdCliente, Long cdModalidade, String cdOperOrigem, String autCdUsuarioSolicitacao, AutExtTexExtratoDTO dto) {
		texExtratoRepository
				.findById(new TexEntratoId(cdEmpresa, cdCliente, cdModalidade, cdOperOrigem))
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
//		if(autExtTexExtratoRepository.existsByCdEmpresaAndCdClienteAndCdModalidadeAndCdOperOrigemAndAutStatus(cdEmpresa, cdCliente, cdModalidade, cdOperOrigem, AutStatus.PENDENTE))
//			throw new AutStatusException("Já existe uma solicitação pendente para este extrato.");
		AutExtTexExtrato entity = modelMapper.map(dto, AutExtTexExtrato.class);
		entity.setAutCdUsuarioSolicitacao(autCdUsuarioSolicitacao);
		entity.setAutDsTipoOperacao(AutDsTipoOperacao.UPDATE);
		entity.setAutStatus(AutStatus.PENDENTE);
		entity.setAutDtSolicitacao(new Date());
		entity.setCdEmpresa(dto.getCdEmpresa() != null ? dto.getCdEmpresa() : cdEmpresa);
		entity.setCdCliente(dto.getCdCliente() != null ? dto.getCdCliente() : cdCliente);
		entity.setCdModalidade(dto.getCdModalidade() != null ? dto.getCdModalidade() : cdModalidade);
		entity.setCdOperOrigem(dto.getCdOperOrigem() != null ? dto.getCdOperOrigem() : cdOperOrigem);
		entity.setOldCdEmpresa(cdEmpresa);
		entity.setOldCdCliente(cdCliente);
		entity.setOldCdModalidade(cdModalidade);
		entity.setOldCdOperOrigem(cdOperOrigem);
		entity.setDtReferencia(extractRepositoryImpl.getRefereceDate());
		return modelMapper.map(autExtTexExtratoRepository.save(entity), AutExtTexExtratoDTO.class);
	}
	
	public AutExtTexExtratoDTO delete(Long cdEmpresa, Long cdCliente, Long cdModalidade, String cdOperOrigem, String autCdUsuarioSolicitacao) {
		TexExtrato extrato = texExtratoRepository
				.findById(new TexEntratoId(cdEmpresa, cdCliente, cdModalidade, cdOperOrigem))
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		AutExtTexExtrato entity = modelMapper.map(extrato, AutExtTexExtrato.class);
		if(autExtTexExtratoRepository.existsByCdEmpresaAndCdClienteAndCdModalidadeAndCdOperOrigemAndAutStatus(entity.getCdEmpresa(), entity.getCdCliente(), entity.getCdModalidade(), entity.getCdOperOrigem(), AutStatus.PENDENTE))
			throw new AutStatusException("Já existe uma solicitação pendente para este extrato.");
		entity.setAutCdUsuarioSolicitacao(autCdUsuarioSolicitacao);
		entity.setAutDsTipoOperacao(AutDsTipoOperacao.DELETE);
		entity.setAutStatus(AutStatus.PENDENTE);
		entity.setAutDtSolicitacao(new Date());
		return modelMapper.map(autExtTexExtratoRepository.save(entity), AutExtTexExtratoDTO.class);
	}
	
	public Page<AutExtTexExtratoDTO> findPage(AutExtExtractFilter autExtExtractFilter, Pageable page) {
		Page<AutExtTexExtratoDTO> autExtExtratos = autExtTexExtratoRepositoryImpl.findPage(autExtExtractFilter, page);
		if (CollectionUtils.isEmpty(autExtExtratos.getContent()))
			throw new EmptyResultDataAccessException(1);
		return autExtExtratos;
	}
	
	public AutExtTexExtratoDTO find(Long autCdSequencial) {
		return modelMapper.map(autExtTexExtratoRepository.findById(autCdSequencial).orElseThrow(() -> new EmptyResultDataAccessException(1)), AutExtTexExtratoDTO.class);
	}
	
	public AutExtTexExtratoDTO rejeitar(Long autCdSequencial, String autCdUsuarioAutorizacao) {
		AutExtTexExtrato entity = autExtTexExtratoRepository.findById(autCdSequencial).orElseThrow(() -> new EmptyResultDataAccessException(1));
		if(!AutStatus.PENDENTE.equals(entity.getAutStatus()))
			throw new AutStatusException("Solicitação não está pendente.");
		if(entity.getAutCdUsuarioSolicitacao().equals(autCdUsuarioAutorizacao))
			throw new AutStatusException("O(a) usuário(a) não pode rejeitar uma alteração solicitada por ele(a) mesmo(a).");
		entity.setAutStatus(AutStatus.REJEITADO);
		entity.setAutCdUsuarioAutorizacao(autCdUsuarioAutorizacao);
		entity.setAutDtAutorizacao(new Date());
		return modelMapper.map(autExtTexExtratoRepository.save(entity), AutExtTexExtratoDTO.class);
	}
	
	@Transactional(noRollbackFor = Exception.class)
	public AutExtTexExtratoDTO aprovar(Long autCdSequencial, String autCdUsuarioAutorizacao) {
		AutExtTexExtrato entity = autExtTexExtratoRepository.findById(autCdSequencial).orElseThrow(() -> new EmptyResultDataAccessException(1));
		if(!AutStatus.PENDENTE.equals(entity.getAutStatus()))
			throw new AutStatusException("Solicitação não está pendente.");
		if(entity.getAutCdUsuarioSolicitacao().equals(autCdUsuarioAutorizacao))
			throw new AutStatusException("O(a) usuário(a) não pode aprovar uma alteração solicitada por ele(a) mesmo(a).");
		if(AutDsTipoOperacao.INSERT.equals(entity.getAutDsTipoOperacao())) {
			insertExtract(entity);
		} else if(AutDsTipoOperacao.UPDATE.equals(entity.getAutDsTipoOperacao())) {
			deleteExtract(entity);
			insertExtract(entity);
		} else if(AutDsTipoOperacao.DELETE.equals(entity.getAutDsTipoOperacao())) {
			deleteExtract(entity);
		} else {
			throw new NotImplementedException("Tratamento para o autStatus não está implementado.");
		}
		entity.setAutStatus(AutStatus.APROVADO);
		entity.setAutCdUsuarioAutorizacao(autCdUsuarioAutorizacao);
		entity.setAutDtAutorizacao(new Date());
		return modelMapper.map(autExtTexExtratoRepository.save(entity), AutExtTexExtratoDTO.class);
	}

	private void deleteExtract(AutExtTexExtrato entity) {
		TexExtrato extrato = texExtratoRepository
				.findById(new TexEntratoId(entity.getOldCdEmpresa(), entity.getOldCdCliente(), entity.getOldCdModalidade(), entity.getOldCdOperOrigem()))
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		texExtratoRepository.delete(extrato);
	}

	private void insertExtract(AutExtTexExtrato entity) {
		TexExtrato saveTexExtrato = new TexExtrato();
		saveTexExtrato.setTexEntratoId(new TexEntratoId(entity.getCdEmpresa(), entity.getCdCliente(), entity.getCdModalidade(), entity.getCdOperOrigem()));
		saveTexExtrato.setCdContrato(entity.getCdContrato());
		saveTexExtrato.setDtReferencia(entity.getDtReferencia());
		saveTexExtrato.setDtInicioOper(entity.getDtInicioOper());
		saveTexExtrato.setDtVencimentoOper(entity.getDtVencimentoOper());
		saveTexExtrato.setDtLiquidacaoOper(entity.getDtLiquidacaoOper());
		saveTexExtrato.setCdGrprod(entity.getCdGrprod());
		saveTexExtrato.setCdFamprod(entity.getCdFamprod());
		saveTexExtrato.setCdProduto(entity.getCdProduto());
		saveTexExtrato.setSgSistema(entity.getSgSistema());
		saveTexExtrato.setSgModulo(entity.getSgModulo());
		saveTexExtrato.setCdMoeda(entity.getCdMoeda());
		saveTexExtrato.setQuantidade(entity.getQuantidade());
		saveTexExtrato.setVlContabil(entity.getVlContabil());
		saveTexExtrato.setVlCorrigidoMe(entity.getVlCorrigidoMe());
		saveTexExtrato.setVlOperacaoMe(entity.getVlOperacaoMe());
		saveTexExtrato.setVlOperacaoMn(entity.getVlOperacaoMn());
		saveTexExtrato.setVlOperacaoEmp(entity.getVlOperacaoEmp());
		saveTexExtrato.setVlJurosMe(entity.getVlJurosMe());
		saveTexExtrato.setVlJurosMn(entity.getVlJurosMn());
		saveTexExtrato.setIiTipoContabil(entity.getIiTipoContabil());
		saveTexExtrato.setCdCgcCpf(entity.getCdCgcCpf());
		saveTexExtrato.setNmCliente(entity.getNmCliente());
		saveTexExtrato.setDcEnd1(entity.getDcEnd1());
		saveTexExtrato.setDcEnd2(entity.getDcEnd2());
		saveTexExtrato.setDcEnd3(entity.getDcEnd3());
		saveTexExtrato.setCdPais(entity.getCdPais());
		saveTexExtrato.setNomeAosCuidados(entity.getNomeAosCuidados());
		saveTexExtrato.setDsObn(entity.getDsObn());
		saveTexExtrato.setNmEmpresa(entity.getNmEmpresa());
		saveTexExtrato.setCdFilial(entity.getCdFilial());
		saveTexExtrato.setNmIndexador(entity.getNmIndexador());
		saveTexExtrato.setCdGarantia(entity.getCdGarantia());
		texExtratoRepository.save(saveTexExtrato);
	}

}
