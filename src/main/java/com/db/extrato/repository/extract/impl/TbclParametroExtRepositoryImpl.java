package com.db.extrato.repository.extract.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.TbclParametroExtFilter;
import com.db.extrato.domain.extract.TbclParametroExt;
import com.db.extrato.dto.TbclParametroExtDTO;
import com.db.extrato.util.Constants;

@Repository
public class TbclParametroExtRepositoryImpl{

  @PersistenceContext
  private EntityManager em;
  
  public Page<TbclParametroExtDTO> pesquisarPage(TbclParametroExtFilter tbclParametroExtFilter, Pageable page) {

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<TbclParametroExt> criteria = criteriaBuilder.createQuery(TbclParametroExt.class);
    Root<TbclParametroExt> root = criteria.from(TbclParametroExt.class);
    
    Predicate[] predicates = criarRestricoes(tbclParametroExtFilter, criteriaBuilder, root);
    criteria.where(predicates);
    criteria.orderBy(criteriaBuilder.desc(root.get("cdSeqModalidade")));
    TypedQuery<TbclParametroExt> query = em.createQuery(criteria);

    
    int paginaAtual = page.getPageNumber();
    int totalRegistrosPorPagina = page.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPorPagina); 
    
    List<TbclParametroExt> parameterModality = query.getResultList();
    
    if (CollectionUtils.isEmpty(parameterModality))
      throw new EmptyResultDataAccessException(1);
    
    List<TbclParametroExtDTO> parameterModalityDto = new ArrayList<TbclParametroExtDTO>();

    parameterModality.forEach(p -> {
      parameterModalityDto.add(TbclParametroExtDTO.convertDto(p));
    });
    
    return new PageImpl<>(parameterModalityDto, page, total(tbclParametroExtFilter));

  }
  
  private Predicate[] criarRestricoes(TbclParametroExtFilter TbclParametroExttFilterFilter, CriteriaBuilder criteriaBuilder,
      Root<TbclParametroExt> root) {

    List<Predicate> predicates = new ArrayList<>();

    if (TbclParametroExttFilterFilter.getCdModalidade() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_MODALIDADE), TbclParametroExttFilterFilter.getCdModalidade()));
    }
    
    if (TbclParametroExttFilterFilter.getCdSisOrig() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_SIS_ORIG), TbclParametroExttFilterFilter.getCdSisOrig()));
    }
    
    if (TbclParametroExttFilterFilter.getIddDCamd() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CAMPO_ORIGEM), TbclParametroExttFilterFilter.getIddDCamd()));
    }

    if (TbclParametroExttFilterFilter.getIddDCaex() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CAMPO_DESTINO), TbclParametroExttFilterFilter.getIddDCaex()));
    }
    
    return predicates.toArray(new Predicate[predicates.size()]);
  }
  
  public Long total(TbclParametroExtFilter TbclParametroExttFilterFilter) {

    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TbclParametroExt> root = criteria.from(TbclParametroExt.class);

    Predicate[] predicate = criarRestricoes(TbclParametroExttFilterFilter, builder, root);
    criteria.where(predicate);

    criteria.select(builder.count(root));

    return em.createQuery(criteria).getSingleResult();
  }
  
}
