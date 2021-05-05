package com.db.extrato.repository.extract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.ClientExtractManagementFilter;
import com.db.extrato.domain.extract.ClientExtractManagement;
import com.db.extrato.dto.ClientExtractManagementDTO;
import com.db.extrato.dto.ClientExtractManagementUniqueDTO;
import com.db.extrato.enums.VrHorario;
import com.db.extrato.util.Constants;

import lombok.extern.java.Log;

@Log
@Repository
public class ClientExtractManagementRepositoryImpl {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public ClientExtractManagementDTO findClientInExtract(ClientExtractManagementFilter clientExtractManagementFilter, Pageable page) {
	  
	log.info("ClientExtractManagementDTO.find => Inicio => clientExtractManagementFilter => [" + clientExtractManagementFilter.toString() + "]");  

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<ClientExtractManagement> criteria = criteriaBuilder.createQuery( ClientExtractManagement.class);
    
	log.info("ClientExtractManagementDTO.find => Montou Criteria");  

	Root<ClientExtractManagement> root = criteria.from(ClientExtractManagement.class);
    
	log.info("ClientExtractManagementDTO.find => criteria.from");  
	
    Predicate[] predicates = criarRestricoes(clientExtractManagementFilter, criteriaBuilder, root);
    criteria.where(predicates);
    
	log.info("ClientExtractManagementDTO.find => criteria.where(predicates)");  
	
    TypedQuery<ClientExtractManagement> query = em.createQuery(criteria);
    
	log.info("ClientExtractManagementDTO.find => TypedQuery<ClientExtractManagement> query = em.createQuery(criteria)");  
	
    int paginaAtual = page.getPageNumber();
	log.info("ClientExtractManagementDTO.find => paginaAtual + [" + paginaAtual + "]");  
    int totalRegistrosPOrOagina = page.getPageSize();
	log.info("ClientExtractManagementDTO.find => totalRegistrosPOrOagina + [" + totalRegistrosPOrOagina + "]");  
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPOrOagina;
	log.info("ClientExtractManagementDTO.find => primeiroRegistroDaPagina + [" + primeiroRegistroDaPagina + "]");  

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPOrOagina);    
    
    List<ClientExtractManagementDTO> listaClientDto = new ArrayList<>();
    
    List<ClientExtractManagement> client = query.getResultList();
    
	log.info("ClientExtractManagementDTO.find => client + [" + client.toString() + "]");  

    if ((client != null) && (!client.isEmpty())) {    
	    client.forEach(c -> {
	    	ClientExtractManagementDTO d = new ClientExtractManagementDTO();
	    	d.setCdCliente(c.getClientExtractManagementId().getCdCliBr());
	    	d.setVrPeriodo(c.getClientExtractManagementId().getVrPeriodo());
	    	d.setDtRef((c.getDtRef() == null) ? Constants.SPACE_1: c.getDtRef());
	    	d.setCpfCnpjCliente(Constants.SPACE_1);
	    	d.setIsInsertedOnExtract(false);
	    	d.setNomeCliente(c.getNmCli());
	    	d.setVrAgendamento((c.getVrAgendamento() == null || c.getVrAgendamento().trim().isEmpty()) ? Constants.ZERO : c.getVrAgendamento());
	    	d.setVrDiaSemana((c.getVrDiaSemana() == null) ? Constants.SPACE_1 : c.getVrDiaSemana());
	    	d.setVrEmailFlag((c.getVrEmailFlag() == null || c.getVrEmailFlag().trim().isEmpty()) ? Constants.SPACE_1 : c.getVrEmailFlag());
	    	d.setVrHorario((c.getVrHorario() == null) ? Constants.SPACE_1 : c.getVrHorario());
	    	d.setVrTipoRelatorio((c.getVrReportType() == null) ? Constants.SPACE_1 : c.getVrReportType());
	    	d.setCdSitPessoa(Constants.SPACE_1);
	    	listaClientDto.add(d);
	    });

	    log.info("ClientExtractManagementDTO.find => return client + [" + listaClientDto.get(0) + "]");  
	    
	    return listaClientDto.get(0);
    } else {
    	log.info("Retornou ClientExtractManagementDTO.find RETORNOU NULL");
    	return null;
    }
  }
  
  public List<ClientExtractManagementDTO> findAllClientsInExtract(ClientExtractManagementFilter clientExtractManagementFilter, Pageable page) {
	  
	log.info("ClientExtractManagementDTO.find => Inicio => clientExtractManagementFilter => [" + clientExtractManagementFilter.toString() + "]");  

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<ClientExtractManagement> criteria = criteriaBuilder.createQuery( ClientExtractManagement.class);
    
	log.info("ClientExtractManagementDTO.find => Montou Criteria");  

	Root<ClientExtractManagement> root = criteria.from(ClientExtractManagement.class);
    
	log.info("ClientExtractManagementDTO.find => criteria.from");  
	
    Predicate[] predicates = criarRestricoes(clientExtractManagementFilter, criteriaBuilder, root);
    criteria.where(predicates);
    
	log.info("ClientExtractManagementDTO.find => criteria.where(predicates)");  
	
    TypedQuery<ClientExtractManagement> query = em.createQuery(criteria);
    
	log.info("ClientExtractManagementDTO.find => TypedQuery<ClientExtractManagement> query = em.createQuery(criteria)");  
	
    int paginaAtual = page.getPageNumber();
	log.info("ClientExtractManagementDTO.find => paginaAtual + [" + paginaAtual + "]");  
    int totalRegistrosPOrOagina = page.getPageSize();
	log.info("ClientExtractManagementDTO.find => totalRegistrosPOrOagina + [" + totalRegistrosPOrOagina + "]");  
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPOrOagina;
	log.info("ClientExtractManagementDTO.find => primeiroRegistroDaPagina + [" + primeiroRegistroDaPagina + "]");  

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPOrOagina);    
    
    List<ClientExtractManagementDTO> listaClientDtos = new ArrayList<>();
    
    List<ClientExtractManagement> client = query.getResultList();
    
	log.info("ClientExtractManagementDTO.find => client + [" + client.toString() + "]");  

    if ((client != null) && (!client.isEmpty())) {    
	    client.forEach(c -> {
	    	ClientExtractManagementDTO d = new ClientExtractManagementDTO();
	    	d.setCdCliente(c.getClientExtractManagementId().getCdCliBr());
	    	d.setVrPeriodo(c.getClientExtractManagementId().getVrPeriodo());
	    	d.setDtRef((c.getDtRef() == null) ? Constants.SPACE_1: c.getDtRef());
	    	d.setCpfCnpjCliente(Constants.SPACE_1);
	    	d.setIsInsertedOnExtract(true);
	    	d.setNomeCliente(c.getNmCli());
	    	d.setVrAgendamento((c.getVrAgendamento() == null || c.getVrAgendamento().trim().isEmpty()) ? Constants.ZERO : c.getVrAgendamento());
	    	d.setVrDiaSemana((c.getVrDiaSemana() == null) ? Constants.SPACE_1 : c.getVrDiaSemana());
	    	d.setVrEmailFlag((c.getVrEmailFlag() == null || c.getVrEmailFlag().trim().isEmpty()) ? Constants.SPACE_1 : c.getVrEmailFlag());
	    	d.setVrHorario((c.getVrHorario() == null) ? Constants.SPACE_1 : c.getVrHorario());
	    	d.setVrTipoRelatorio((c.getVrReportType() == null) ? Constants.SPACE_1 : c.getVrReportType());
	    	d.setCdSitPessoa(Constants.SPACE_1);
	    	listaClientDtos.add(d);
	    });

	    log.info("ClientExtractManagementDTO.find => return client + [" + listaClientDtos + "]");  
	    
	    return listaClientDtos;
    } else {
    	log.info("Retornou ClientExtractManagementDTO.find RETORNOU NULL");
    	return null;
    }
  }

  
  public ClientExtractManagement find(ClientExtractManagementFilter clientExtractManagementFilter) {

	    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	    CriteriaQuery<ClientExtractManagement> criteria = criteriaBuilder.createQuery( ClientExtractManagement.class);
	    
	    Root<ClientExtractManagement> root = criteria.from(ClientExtractManagement.class);
	    
	    Predicate[] predicates = criarRestricoes(clientExtractManagementFilter, criteriaBuilder, root);
	    criteria.where(predicates);
	    
	    TypedQuery<ClientExtractManagement> query = em.createQuery(criteria);
	    	    
	    List<ClientExtractManagement> client = query.getResultList();
	    
	    if ((client != null) && (!client.isEmpty())) {    
		    return client.get(0);
	    } else {
	    	return null;
	    }
  }

  
  private Predicate[] criarRestricoes(ClientExtractManagementFilter clientExtractManagementFilter, CriteriaBuilder criteriaBuilder,
      Root<ClientExtractManagement> root) {

    List<Predicate> predicates = new ArrayList<>();

    if (clientExtractManagementFilter.getCdCliBr() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_EXTRACT_CLEINT_MANAGEMENT_ID).get(Constants.ATTR_CD_CLI_BR), clientExtractManagementFilter.getCdCliBr()));
    }

    if (clientExtractManagementFilter.getVrPeriodo() != null) {
      predicates.add(criteriaBuilder.like(root.get(Constants.ATTR_EXTRACT_CLEINT_MANAGEMENT_ID).get(Constants.ATTR_VR_PERIODO), clientExtractManagementFilter.getVrPeriodo()));
    }
    
    if (clientExtractManagementFilter.getNmCli() != null) {
      predicates.add(criteriaBuilder.like(root.get(Constants.ATTR_NM_CLI), "%" + clientExtractManagementFilter.getNmCli() + "%"));
    }

    return predicates.toArray(new Predicate[predicates.size()]);
  }
  
  public Long total(ClientExtractManagementFilter clientExtractManagementFilter) {

	    CriteriaBuilder builder = em.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<ClientExtractManagement> root = criteria.from(ClientExtractManagement.class);

	    Predicate[] predicate = criarRestricoes(clientExtractManagementFilter, builder, root);
	    criteria.where(predicate);

	    criteria.select(builder.count(root));

	    return em.createQuery(criteria).getSingleResult();
  }
  
  public Long total(String clientName) {
		StringBuilder query = new StringBuilder();

		query.append(" SELECT ");
		
		query.append(" UNIQUE CD_CLI_BR  ");
		query.append(" FROM EXTRATO_APP.CLIENT_EXTRACT_MANAGEMENT c, EXTRATO_APP.TBDW_DIM_PESSOA p WHERE 1=1 ");
		query.append(" AND p.CD_PESSOA = c.CD_CLI_BR ");
		query.append(" AND UPPER(c.NM_CLI) LIKE '%" + clientName.toUpperCase() + "%'");
		
		@SuppressWarnings("unchecked")
		List<Object> lista = em.createNativeQuery(query.toString()).getResultList();
		
		Long total = (long) lista.size();
		    
		return total;
  }
  
  public List<ClientExtractManagementUniqueDTO> findUniqueClientExtractManagemnt(String clientName, Pageable page) {
	  	  
	  StringBuilder query = new StringBuilder();
	  
	  query.append(" SELECT ");
	  query.append(" UNIQUE CD_CLI_BR, NM_CLI, VR_AGENDAMENTO  ");
	  query.append(" FROM EXTRATO_APP.CLIENT_EXTRACT_MANAGEMENT ");
	  query.append(" WHERE UPPER(NM_CLI) LIKE '%" + clientName.toUpperCase() + "%'");
	  query.append(" ORDER BY VR_AGENDAMENTO DESC, CD_CLI_BR ASC, NM_CLI ASC ");
	  query.append(" OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY");
	  
	  List<ClientExtractManagementUniqueDTO> lista = jdbcTemplate.query(query.toString(), new RowMapper<ClientExtractManagementUniqueDTO>() {
			@Override
			public ClientExtractManagementUniqueDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClientExtractManagementUniqueDTO dto = new ClientExtractManagementUniqueDTO();
				dto.setCdCliBr(rs.getLong("CD_CLI_BR"));
				dto.setNmCli(rs.getString("NM_CLI"));
				dto.setVrAgendamento((rs.getString("VR_AGENDAMENTO") == null ||
						rs.getString("VR_AGENDAMENTO").trim().isEmpty()) ? Constants.ZERO : rs.getString("VR_AGENDAMENTO"));
			return dto;
			}
		})
		.stream()
		.map(row -> ClientExtractManagementUniqueDTO.class.cast(row))
		.collect(Collectors.toList());

	  return lista;
  }
  
  public List<ClientExtractManagementDTO> findClientesAgendamento(Date data, Integer vrAgendamento, VrHorario horario, String diaSemana, Integer ultimoDiaMes) {
		
		log.info("Date: [" +  data + "]");
		log.info("vrAgendamento + [" + vrAgendamento + "]");
		log.info("VrHorario [" + horario + "]");
		log.info("diaSemana [" + diaSemana + "]");
		log.info("ultimoDiaMes [" + ultimoDiaMes + "]");
		
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(" CD_CLI_BR, NM_CLI, VR_AGENDAMENTO, VR_PERIODO, VR_HORARIO, VR_DIA_SEMANA, DT_REF, VR_TIPO_RELATORIO, VR_EMAIL_FLAG ");
		query.append(" FROM EXTRATO_APP.CLIENT_EXTRACT_MANAGEMENT M  ");
		query.append(" WHERE VR_AGENDAMENTO = " + vrAgendamento + " ");
		query.append(" AND (VR_PERIODO = 'DIARIO' AND VR_HORARIO = DECODE('" + horario.getVrHorario() + "', '05:00', 'MANHA', ");
		query.append("                                                             '10:00', 'TARDE', ");
		query.append("                                                             '22:00', 'NOITE')) OR ");
		query.append(" (VR_PERIODO = 'SEMANAL' AND VR_HORARIO = DECODE('" + horario.getVrHorario() + "', '05:00', 'MANHA', ");
		query.append("                                                              '10:00', 'TARDE', ");
		query.append("                                                              '22:00', 'NOITE') AND VR_DIA_SEMANA = upper('" + diaSemana + "')) OR ");
		query.append(" (VR_PERIODO = 'MENSAL' AND '" + ultimoDiaMes + "' = '0' AND VR_HORARIO = DECODE('" + horario.getVrHorario() + "', '05:00', 'MANHA', ");
		query.append("                                                                                          '10:00', 'TARDE', ");
		query.append("                                                                                          '22:00', 'NOITE') ");
		query.append("                                                                     AND DT_REF = to_char(EXTRATO_APP.PROXIMO_DIA_UTIL(TO_DATE('" + new SimpleDateFormat("dd/MM/yyyy").format(data) + "', 'DD/MM/YYYY')), 'dd')) OR ");
		query.append(" (VR_PERIODO = 'MENSAL' AND '" + ultimoDiaMes + "' = '1' AND VR_HORARIO = DECODE('" + horario.getVrHorario() + "', '05:00', 'MANHA', ");
		query.append("                                                                                          '10:00', 'TARDE', ");
		query.append("                                                                                          '22:00', 'NOITE') ");
		query.append("                                                                     AND DT_REF >= to_char(EXTRATO_APP.PROXIMO_DIA_UTIL(TO_DATE('" + new SimpleDateFormat("dd/MM/yyyy").format(data) + "', 'DD/MM/YYYY')), 'dd')) ");
	
		log.info("Query + [" + query + "]");
		
		return jdbcTemplate.query(query.toString(), new RowMapper<ClientExtractManagementDTO>() {
			@Override
			public ClientExtractManagementDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClientExtractManagementDTO dto = new ClientExtractManagementDTO();
				dto.setCdCliente(rs.getLong("CD_CLI_BR"));
				dto.setNomeCliente(rs.getString("NM_CLI"));
				dto.setVrAgendamento((rs.getString("VR_AGENDAMENTO") == null ||
						rs.getString("VR_AGENDAMENTO").trim().isEmpty()) ? Constants.ZERO : rs.getString("VR_AGENDAMENTO"));
				dto.setVrPeriodo(rs.getString("VR_PERIODO"));
				dto.setVrHorario((rs.getString("VR_HORARIO") == null ||
						rs.getString("VR_HORARIO").trim().isEmpty()) ? Constants.SPACE_1 : rs.getString("VR_HORARIO"));
				dto.setVrDiaSemana((rs.getString("VR_DIA_SEMANA") == null ||
						rs.getString("VR_DIA_SEMANA").trim().isEmpty()) ? Constants.SPACE_1 : rs.getString("VR_DIA_SEMANA"));
				dto.setDtRef((rs.getString("DT_REF") == null ||
						rs.getString("DT_REF").isEmpty()) ? Constants.SPACE_1  : rs.getString("DT_REF"));
				dto.setVrTipoRelatorio((rs.getString("VR_TIPO_RELATORIO") == null ||
						rs.getString("VR_TIPO_RELATORIO").trim().isEmpty()) ? Constants.SPACE_1 : rs.getString("VR_TIPO_RELATORIO"));
				dto.setVrEmailFlag((rs.getString("VR_EMAIL_FLAG") == null ||
						rs.getString("VR_EMAIL_FLAG").trim().isEmpty() ||
						rs.getString("VR_EMAIL_FLAG").equals(Constants.SPACE_1)) ? "NAO" : rs.getString("VR_EMAIL_FLAG"));
				return dto;
			}
		})
		.stream()
		.map(row -> ClientExtractManagementDTO.class.cast(row))
		.collect(Collectors.toList());
	}
  
  
  public List<ClientExtractManagementDTO> findPageManutencaoCliente(String clientName, Pageable page) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" SELECT ROWNUM as LINE, 'true' as ADICIONADO, c.CD_CLI_BR as CODCLI, c.NM_CLI as NOMECLI, ");
		query.append(" c.VR_AGENDAMENTO as AGENDAMENTO, c.VR_PERIODO, c.VR_HORARIO, c.VR_DIA_SEMANA, c.DT_REF, ");
		query.append(" c.VR_TIPO_RELATORIO,  c.VR_EMAIL_FLAG, p.CD_CGC_CPF as CGCCPF ");  
		query.append(" FROM EXTRATO_APP.CLIENT_EXTRACT_MANAGEMENT c, EXTRATO_APP.TBDW_DIM_PESSOA p ");
		query.append(" WHERE c.CD_CLI_BR = p.CD_PESSOA ");
		query.append(" AND UPPER(c.NM_CLI) LIKE '%" + clientName.toUpperCase() + "%' ");
		query.append(" UNION ");
		query.append(" SELECT ROWNUM as LINE, 'false' as ADICIONADO , p.CD_PESSOA as CODCLI, p.NM_PESSOA as NOMECLI, ");
		query.append(" ' ' as AGENDAMENTO, ' ', ' ', ' ', ' ', ' ', ' ', p.CD_CGC_CPF as CGCCPF ");  
		query.append(" FROM EXTRATO_APP.TBDW_DIM_PESSOA p ");
		query.append(" WHERE p.CD_PESSOA NOT IN (SELECT CD_CLI_BR FROM EXTRATO_APP.CLIENT_EXTRACT_MANAGEMENT) ");
		query.append(" AND p.COD_SIT_PESSOA IN ('" + Constants.CLIENTE_ATIVO + "', '" + Constants.CLIENTE_BLOQUEADO + "')" );
		query.append(" AND UPPER(p.NM_PESSOA) LIKE '%" + clientName.toUpperCase() + "%' ");
		query.append(" ORDER BY ADICIONADO DESC, AGENDAMENTO DESC, LINE DESC , CODCLI ASC, NOMECLI ASC ");
		query.append(" OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY "); 

		log.info("Query + [" + query + "]");
		
		return jdbcTemplate.query(query.toString(), new RowMapper<ClientExtractManagementDTO>() {
			@Override
			public ClientExtractManagementDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClientExtractManagementDTO dto = new ClientExtractManagementDTO();
				dto.setIsInsertedOnExtract((rs.getString("ADICIONADO").equals("true") ? true : false));
				dto.setCdCliente(rs.getLong("CODCLI"));
				dto.setNomeCliente(rs.getString("NOMECLI") == null || rs.getString("NOMECLI").trim().isEmpty() ? Constants.SPACE_1 : rs.getString("NOMECLI"));
				dto.setVrAgendamento((rs.getString("AGENDAMENTO") == null ||
						rs.getString("AGENDAMENTO").trim().isEmpty()) ? Constants.ZERO : rs.getString("AGENDAMENTO"));
				dto.setVrPeriodo(rs.getString("VR_PERIODO"));
				dto.setVrHorario((rs.getString("VR_HORARIO") == null ||
						rs.getString("VR_HORARIO").trim().isEmpty()) ? Constants.SPACE_1 : rs.getString("VR_HORARIO"));
				dto.setVrDiaSemana((rs.getString("VR_DIA_SEMANA") == null ||
						rs.getString("VR_DIA_SEMANA").trim().isEmpty()) ? Constants.SPACE_1 : rs.getString("VR_DIA_SEMANA"));
				dto.setDtRef((rs.getString("DT_REF") == null ||
						rs.getString("DT_REF").isEmpty()) ? Constants.SPACE_1  : rs.getString("DT_REF"));
				dto.setVrTipoRelatorio((rs.getString("VR_TIPO_RELATORIO") == null ||
						rs.getString("VR_TIPO_RELATORIO").trim().isEmpty()) ? Constants.SPACE_1 : rs.getString("VR_TIPO_RELATORIO"));
				dto.setVrEmailFlag((rs.getString("VR_EMAIL_FLAG") == null ||
						rs.getString("VR_EMAIL_FLAG").trim().isEmpty() ||
						rs.getString("VR_EMAIL_FLAG").equals(Constants.SPACE_1)) ? "NAO" : rs.getString("VR_EMAIL_FLAG"));
				dto.setCpfCnpjCliente((rs.getString("CGCCPF") == null || rs.getString("CGCCPF").trim().isEmpty() ? Constants.SPACE_1 : rs.getString("CGCCPF")));
				return dto;
			}
		})
		.stream()
		.map(row -> ClientExtractManagementDTO.class.cast(row))
		.collect(Collectors.toList());
	}
}

