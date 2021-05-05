package com.db.extrato.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.extrato.controller.filter.ClientExtractManagementFilter;
import com.db.extrato.controller.filter.TbdwDmPessoaFilter;
import com.db.extrato.domain.extract.ClientExtractManagement;
import com.db.extrato.domain.extract.ClientExtractManagementId;
import com.db.extrato.domain.extract.TbdwDmPessoa;
import com.db.extrato.dto.ClientExtractManagementDTO;
import com.db.extrato.dto.TbdwDmPessoaDTO;
import com.db.extrato.enums.VrPeriodo;
import com.db.extrato.exception.GenericException;
import com.db.extrato.repository.extract.ClientExtractManagementRepository;
import com.db.extrato.repository.extract.TbdwDmPessoaRepository;
import com.db.extrato.repository.extract.impl.ClientExtractManagementRepositoryImpl;
import com.db.extrato.repository.extract.impl.TbdwDmPessoaRepositoryImpl;
import com.db.extrato.util.Constants;

import lombok.extern.java.Log;

@Log
@Service
public class ClientExtractManagementService {
		
	@Autowired
	private TbdwDmPessoaRepositoryImpl tbdwDmPessoaRepositoryImpl;
	
	@Autowired
	private TbdwDmPessoaRepository tbdwDmPessoaRepository;
	
	@Autowired
	private ClientExtractManagementRepositoryImpl clientRepositoryImpl;
	
	@Autowired
	private ClientExtractManagementRepository clientRepository;

	public Page<ClientExtractManagementDTO> getGridExtratoDerivativo(TbdwDmPessoaFilter tbdwDmPessoaFilter, Pageable page) {
				
		var pessoas = tbdwDmPessoaRepositoryImpl.pesquisarPage(tbdwDmPessoaFilter, page);
		
		List<TbdwDmPessoaDTO> pessoaDtos = pessoas.getContent();
		
		List<ClientExtractManagementDTO> clientDtos = new ArrayList<ClientExtractManagementDTO>();
		
		log.info("tbdwDmPessoaFilter cdGcgCpf [" + (tbdwDmPessoaFilter.getCdGcgCpf() == null ? " " : tbdwDmPessoaFilter.getCdGcgCpf()) + "]");
		log.info("tbdwDmPessoaFilter codSitPessoaAtiva [" + (tbdwDmPessoaFilter.getCodSitPessoaAtiva() == null ? " " : tbdwDmPessoaFilter.getCodSitPessoaAtiva()) + "]");
		log.info("tbdwDmPessoaFilter nmPessoa [" + (tbdwDmPessoaFilter.getNmPessoa() == null ? " " : tbdwDmPessoaFilter.getNmPessoa()) + "]");
		log.info("tbdwDmPessoaFilter nmPessoaAbrev [" + (tbdwDmPessoaFilter.getNmPessoaAbrev() == null ? " " : tbdwDmPessoaFilter.getNmPessoaAbrev()) + "]");
		log.info("tbdwDmPessoaFilter codSitPessoaBloqueada [" + (tbdwDmPessoaFilter.getCodSitPessoaBloqueada() == null ? " " : tbdwDmPessoaFilter.getCodSitPessoaBloqueada()) + "]");
	
		if (pessoaDtos == null) {
			throw new GenericException("Lista de Pessoas nula");
		} 
		log.info("pessoaDtos tamanho [" + pessoaDtos.size() + "]");
		
		pessoaDtos.forEach( pessoaDto -> {
			ClientExtractManagementFilter clientExtractManagementFilter = new ClientExtractManagementFilter();
			log.info("CdPessoa [" + (pessoaDto.getCdPessoa() == null ? "null" : pessoaDto.getCdPessoa()) + "]");
			log.info(" clientExtractManagementFilter [ " + clientExtractManagementFilter.toString() + "]");
			
			clientExtractManagementFilter.setCdCliBr(pessoaDto.getCdPessoa());
			
			ClientExtractManagementDTO clientDto = null;
			
			clientDto = clientRepositoryImpl.findClientInExtract(clientExtractManagementFilter, page);
			
			if (clientDto != null) {
				log.info("clientDto != null => Cliente CdPessoa [" + (pessoaDto.getCdPessoa() == null ? "Null" : pessoaDto.getCdPessoa()) + "]");
				clientDto.setIsInsertedOnExtract(true);
				clientDto.setCdCliente(pessoaDto.getCdPessoa());
				clientDto.setCpfCnpjCliente(pessoaDto.getCdGcgCpf().toString());
				clientDto.setNomeCliente(pessoaDto.getNmPessoa());
				clientDto.setCdSitPessoa(pessoaDto.getCodSitPessoa());
				clientDtos.add(clientDto);
			} 
		});
		
		log.info(" Fim - getGridExtratoDerivativoManutencao => clientDtos [" + clientDtos.size() + "]");
		
		return new PageImpl<>(clientDtos, page, clientRepositoryImpl.total(tbdwDmPessoaFilter.getNmPessoa()));
	}
	
	public Page<ClientExtractManagementDTO> getGridExtratoDerivativo(String clientName, Pageable page) {
		
		var uniqueClientDtos = clientRepositoryImpl.findUniqueClientExtractManagemnt(clientName, page);
		
		List<ClientExtractManagementDTO> clientDtos = new ArrayList<ClientExtractManagementDTO>();
		
		uniqueClientDtos.forEach(c -> {			
			ClientExtractManagementDTO clientDto = new ClientExtractManagementDTO();
			
			TbdwDmPessoa pessoa = tbdwDmPessoaRepository.findByCdPessoa(c.getCdCliBr());
			
			String cdSitPessoa = Constants.SPACE_1;
			String cdGcgCpf = Constants.SPACE_1; 
			
			if (pessoa != null) {
				cdSitPessoa = ((pessoa.getCodSitPessoa() == null) ? Constants.SPACE_1 : pessoa.getCodSitPessoa());
				cdGcgCpf = ((pessoa.getCdGcgCpf() == null) ? Constants.SPACE_1 : pessoa.getCdGcgCpf().toString());
			}
			
			log.info("CdCli = [" + c.getCdCliBr() + "]");
			log.info("NmCli = [" + c.getNmCli() + "]");
			log.info("getVrAgendamento = [" + c.getVrAgendamento() + "]");
			
			ClientExtractManagement client = new ClientExtractManagement();
			
			List<ClientExtractManagement> clientList = clientRepository.findByClientExtractManagementIdCdCliBr(c.getCdCliBr());
			
			if (clientList != null && !clientList.isEmpty()) {
				client = clientList.get(0);
			} else {
				throw new GenericException("Erro ao buscar cliente [" + c.getNmCli() + "]");
			}
			
			
			if (client != null) {
				clientDto.setIsInsertedOnExtract(true);
				clientDto.setCdCliente(client.getClientExtractManagementId().getCdCliBr());
				clientDto.setDtRef(
						(client.getDtRef() == null || client.getDtRef().trim().isEmpty()) ? 
								Constants.SPACE_1 : client.getDtRef());
				clientDto.setNomeCliente(
						(client.getNmCli() == null || client.getNmCli().trim().isEmpty() ?
								Constants.SPACE_1 : client.getNmCli()));
				clientDto.setVrAgendamento(
						(client.getVrAgendamento() == null || client.getVrAgendamento().trim().isEmpty() ?
								Constants.ZERO : client.getVrAgendamento()));
				clientDto.setVrDiaSemana(
						(client.getVrDiaSemana() == null || client.getVrDiaSemana().trim().isEmpty() ?
								Constants.SPACE_1 : client.getVrDiaSemana()));
				clientDto.setVrEmailFlag(
						(client.getVrEmailFlag() == null || client.getVrEmailFlag().trim().isEmpty() ?
								Constants.SPACE_1 : client.getVrEmailFlag()));
				clientDto.setVrHorario(
						(client.getVrHorario() == null || client.getVrHorario().trim().isEmpty() ?
								Constants.SPACE_1 : client.getVrHorario()));
				clientDto.setVrPeriodo(
						(client.getClientExtractManagementId().getVrPeriodo() == null ||
								client.getClientExtractManagementId().getVrPeriodo().trim().isEmpty() ?
										Constants.SPACE_1 : client.getClientExtractManagementId().getVrPeriodo()));
				clientDto.setVrTipoRelatorio(
						(client.getVrReportType() == null || client.getVrReportType().trim().isEmpty() ?
								Constants.SPACE_1 : client.getVrReportType()));
				clientDto.setCdSitPessoa(cdSitPessoa);
				clientDto.setCpfCnpjCliente(cdGcgCpf);
				clientDtos.add(clientDto);
			}
		});
		
		return new PageImpl<>(clientDtos, page, clientRepositoryImpl.total(clientName));
	}

	public Page<ClientExtractManagementDTO> getGridExtratoDerivativoManutencao(String clientName, Pageable page) {
		
		var pessoas = clientRepositoryImpl.findPageManutencaoCliente(clientName, page);
		
		TbdwDmPessoaFilter tbdwDmPessoaFilter = new TbdwDmPessoaFilter();
		tbdwDmPessoaFilter.setCodSitPessoaAtiva(Constants.CLIENTE_ATIVO);
		tbdwDmPessoaFilter.setCodSitPessoaBloqueada(Constants.CLIENTE_BLOQUEADO);
						
		return new PageImpl<>(pessoas, page, tbdwDmPessoaRepositoryImpl.total(tbdwDmPessoaFilter));
	}
	
	@Transactional
	public ClientExtractManagement insert(ClientExtractManagementDTO dto) {
						
		ClientExtractManagement client;
		
		ClientExtractManagementId clientId = new ClientExtractManagementId();
		
		clientId.setCdCliBr(dto.getCdCliente());
		
		String vrPeriodo;
		
		if (dto.getVrPeriodo() == null || dto.getVrPeriodo().trim().isEmpty()) {
			vrPeriodo = Constants.NENHUM;
		} else {
			vrPeriodo = dto.getVrPeriodo();
		}
		
		clientId.setVrPeriodo(vrPeriodo);
		
		client = clientRepository.findByClientExtractManagementId(clientId);
			
		if (client == null) {
			client = new ClientExtractManagement();
		}
		
		client.setClientExtractManagementId(clientId);
		
		String DtRef;
		
		if (dto.getDtRef() == null || dto.getDtRef().trim().isEmpty()) {
			DtRef = Constants.SPACE_1;
		} else {
			DtRef = StringUtils.leftPad(dto.getDtRef(), 2, "0");
		}
		
		client.setDtRef(DtRef);
	
		String nomeCliente;
		
		if (dto.getNomeCliente() == null || dto.getNomeCliente().trim().isEmpty()) {
			nomeCliente = Constants.SPACE_1;
		} else {
			nomeCliente = dto.getNomeCliente();
		}
		
		client.setNmCli(nomeCliente);
		
		String vrAgendamento;
		
		if (dto.getVrAgendamento() == null || dto.getVrAgendamento().trim().isEmpty()) {
			vrAgendamento = Constants.ZERO;
		} else {
			vrAgendamento = dto.getVrAgendamento();
		}
		
		client.setVrAgendamento(vrAgendamento);	

		String vrDiaSemana;
		
		if (dto.getVrDiaSemana() == null || dto.getVrDiaSemana().trim().isEmpty() || VrPeriodo.MENSAL.getPeriod().equals(clientId.getVrPeriodo())) {
			vrDiaSemana = Constants.SPACE_1;
		} else {
			vrDiaSemana = dto.getVrDiaSemana();
		}
		
		client.setVrDiaSemana(vrDiaSemana);
		
		String vrEmailFlag;
		
		if (dto.getVrEmailFlag() == null || dto.getVrEmailFlag().trim().isEmpty()) {
			vrEmailFlag = Constants.SPACE_1;
		} else {
			vrEmailFlag = dto.getVrEmailFlag();
		}
		
		client.setVrEmailFlag(vrEmailFlag);
		
		String vrHorario;
		
		if (dto.getVrHorario() == null || dto.getVrHorario().trim().isEmpty()) {
			vrHorario = Constants.SPACE_1;
		} else {
			vrHorario = getVrHorario(dto.getVrHorario());
		}
		
		client.setVrHorario(vrHorario);
		
		String vrReportType;
		
		if (dto.getVrTipoRelatorio() == null || dto.getVrTipoRelatorio().trim().isEmpty()) {
			vrReportType = Constants.SPACE_1;
		} else {
			vrReportType = dto.getVrTipoRelatorio();
		}
		
		client.setVrReportType(vrReportType);
		
		log.info("Vr_Agendamento [" + dto.getVrAgendamento() + "]");
				
		return clientRepository.saveAndFlush(client);
	}
	
	@Transactional
	public Boolean delete(ClientExtractManagementDTO dto) {
		Boolean deleted = true;
		
		ClientExtractManagementFilter filter = new ClientExtractManagementFilter();
		
		filter.setCdCliBr(dto.getCdCliente());
		filter.setVrPeriodo(dto.getVrPeriodo());
		
		ClientExtractManagement client = clientRepositoryImpl.find(filter);
						
		try {
			clientRepository.delete(client);
		}
		catch (Exception e) {
			deleted = false;
		}
		return deleted;
	}
	
	@Transactional
	public Boolean deleteAll(ClientExtractManagementDTO dto) {
		Boolean deleted = true;
		
		ClientExtractManagementFilter filter = new ClientExtractManagementFilter();
		
		filter.setCdCliBr(dto.getCdCliente());
		filter.setVrPeriodo(dto.getVrPeriodo());
		
		ClientExtractManagement client = clientRepositoryImpl.find(filter);
						
		try {
			clientRepository.deleteByClientExtractManagementIdCdCliBr(client.getClientExtractManagementId().getCdCliBr());
		}
		catch (Exception e) {
			deleted = false;
		}
		return deleted;
	}

	
	public List<String> isValid(ClientExtractManagementDTO dto) {
		
		List<String> msgErrors = new ArrayList<String>(); 
		
		// Verifico se já existe na tabela
		
		ClientExtractManagement client = getById(dto.getCdCliente(), dto.getVrPeriodo());
		
		if (client != null) {
			String complemento = (dto.getVrPeriodo().equals(Constants.NENHUM) ? Constants.EMPTY : 
				" ou possui agendamento para esse período ["+ dto.getVrPeriodo() + "]");
			
			msgErrors.add("Cliente " + dto.getCdCliente() + " - " + dto.getNomeCliente() + " - já está cadastrado" + complemento);
			return msgErrors;
		}
		
		// Criticas para agendamento diário
		
		if (dto.getVrPeriodo().equals(VrPeriodo.DIARIO.getPeriod())) {
			client = null;
			client = getById(dto.getCdCliente(), VrPeriodo.SEMANAL.getPeriod());
			
			if (client != null) {
				msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
						  + dto.getNomeCliente()  + " - já tem agendamento para o período SEMANAL");
			} else if (client == null) {
				if (dto.getVrHorario() == null || dto.getVrHorario().trim().isEmpty()) {
					msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
							  + dto.getNomeCliente()  + " - para agendamento Diário, Horário deve ser informado.");
				}
			}	
		}
		
		// Criticas para agendamento semanal 
		
		if (dto.getVrPeriodo().equals(VrPeriodo.SEMANAL.getPeriod())) {
			client = null;
			client = getById(dto.getCdCliente(), VrPeriodo.DIARIO.getPeriod());
			
			if (client != null) {
				msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
						  + dto.getNomeCliente()  + " - já tem agendamento para o período DIARIO");
			} else if (client == null) {
				if (dto.getVrHorario() == null || dto.getVrHorario().trim().isEmpty()) {
					msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
							  + dto.getNomeCliente()  + " - para agendamento Semanal, Horário deve ser informado.");
				}
				if (dto.getVrDiaSemana() == null || dto.getVrDiaSemana().trim().isEmpty()) {
					msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
							  + dto.getNomeCliente()  + " - para agendamento Semanal, Dia da Semana deve ser informado.");
				}
			}	
		}
		
		// Criticas para agendamento MENSAL
		
		if (dto.getVrPeriodo().equals(VrPeriodo.MENSAL.getPeriod())) {
			if (dto.getVrHorario() == null || dto.getVrHorario().trim().isEmpty()) {
				msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
						  + dto.getNomeCliente()  + " - para agendamento Mensal, Horário deve ser informado.");
			}
			if (dto.getDtRef() == null || dto.getDtRef().equals("")) {
				msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
						  + dto.getNomeCliente()  + " - para agendamento Mensal, Dia do Mês deve ser informado.");
			}
		}
		
		if (!dto.getVrPeriodo().equals(Constants.NENHUM) && dto.getIsInsertedOnExtract()) {
			if (dto.getVrTipoRelatorio() == null || dto.getVrTipoRelatorio().trim().isEmpty()) {
				msgErrors.add("Cliente " + dto.getCdCliente() + " - " 
						  + dto.getNomeCliente()  + " - para agendamento Tipo de Relatório deve ser informado.");					
			}			
		}
		
		return msgErrors;
	}
	
	public ClientExtractManagement getById(Long codigo, String periodo) {
		ClientExtractManagementId id = new ClientExtractManagementId();
		
		id.setCdCliBr(codigo);
		id.setVrPeriodo(periodo);
		
		return clientRepository.findByClientExtractManagementId(id);
	}
		
	private String getVrHorario(String horario) {
		if (horario.equals("05:00")) {
			return Constants.MANHA;
		} else if (horario.equals("10:00")) {
			return Constants.TARDE;
		} else if (horario.equals("22:00")) {
			return Constants.NOITE;
		} 
		
		return Constants.SPACE_1;
	}
}
