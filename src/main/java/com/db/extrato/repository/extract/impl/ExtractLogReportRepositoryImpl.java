package com.db.extrato.repository.extract.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.ExtractLogReportFilter;
import com.db.extrato.domain.extract.ExtractLogReport;
import com.db.extrato.dto.ExtractLogReportDTO;
import com.db.extrato.util.Constants;

@Repository
public class ExtractLogReportRepositoryImpl {

  @PersistenceContext
  private EntityManager em;
  
  public Page<ExtractLogReportDTO> pesquisarPage(ExtractLogReportFilter filter, Pageable page) {

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<ExtractLogReport> criteria = criteriaBuilder.createQuery(ExtractLogReport.class);
    Root<ExtractLogReport> root = criteria.from(ExtractLogReport.class);
    
    Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root);
    criteria.where(predicates);
    TypedQuery<ExtractLogReport> query = em.createQuery(criteria);
    List<ExtractLogReportDTO> listaDtos = new ArrayList<>();
    
    int paginaAtual = page.getPageNumber();
    int totalRegistrosPOrOagina = page.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPOrOagina;


    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPOrOagina); 
    
    List<ExtractLogReport> logs = query.getResultList();
        
    logs.forEach(l -> {
    	listaDtos.add(ExtractLogReportDTO.convertDto(l));
    });
    
    return new PageImpl<>(listaDtos, page, total(filter));

  }

  public Long total(ExtractLogReportFilter filter) {

    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<ExtractLogReport> root = criteria.from(ExtractLogReport.class);

    Predicate[] predicate = criarRestricoes(filter, builder, root);
    criteria.where(predicate);

    criteria.select(builder.count(root));

    return em.createQuery(criteria).getSingleResult();
  }

  private Predicate[] criarRestricoes(ExtractLogReportFilter filter, CriteriaBuilder cb,
      Root<ExtractLogReport> root) {

    List<Predicate> predicates = new ArrayList<>();

    if (filter.getDtLog() != null) {
    	predicates.add(cb.equal(cb.function("TRUNC", Date.class, root.get(Constants.ATTR_DT_LOG)), filter.getDtLog()));
    }
    
    return predicates.toArray(new Predicate[predicates.size()]);
  }
}
