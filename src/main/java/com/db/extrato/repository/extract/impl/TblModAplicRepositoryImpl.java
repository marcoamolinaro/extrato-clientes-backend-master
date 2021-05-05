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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.TblModAplicFilter;
import com.db.extrato.domain.extract.TbdwDmModalidade;
import com.db.extrato.domain.extract.TblModAplic;
import com.db.extrato.dto.TblModAplicDTO;
import com.db.extrato.repository.extract.TbdwDmModalidadeRepository;
import com.db.extrato.util.Constants;

import lombok.extern.java.Log;

@Log
@Repository
public class TblModAplicRepositoryImpl {

  @PersistenceContext
  private EntityManager em;
  
  @Autowired
  TbdwDmModalidadeRepository tbdwDmModalidadeRepository;
  
  public Page<TblModAplicDTO> pesquisarPage(TblModAplicFilter tblModAplicFilter, Pageable page) {

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<TblModAplic> criteria = criteriaBuilder.createQuery(TblModAplic.class);
    Root<TblModAplic> root = criteria.from(TblModAplic.class);
    
    Predicate[] predicates = criarRestricoes(tblModAplicFilter, criteriaBuilder, root);
    criteria.where(predicates);
    criteria.orderBy(criteriaBuilder.desc(root.get("cdSeqModAplic")));
    TypedQuery<TblModAplic> query = em.createQuery(criteria);
    List<TblModAplicDTO> listaModAplicDto = new ArrayList<>();
    
    int paginaAtual;
    
	if (tblModAplicFilter.getCdModalidade() != null || tblModAplicFilter.getCdSeqModAplic() != null) {
		paginaAtual = 0;
	} else {
		paginaAtual  = page.getPageNumber();
	}

    int totalRegistrosPOrOagina = page.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPOrOagina;
	    
    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPOrOagina); 
    
    List<TblModAplic> modaplic = query.getResultList();
    
    modaplic.forEach(m -> {
    	listaModAplicDto.add(TblModAplicDTO.convertDto(m));   	
    });
    
    listaModAplicDto.forEach(d -> {
    	TbdwDmModalidade modalidade = tbdwDmModalidadeRepository.findByCdModalidade(d.getCdModalidade());
    	if (modalidade == null) {
    		log.info("CdModalidade [" + d.getCdModalidade() + "]. Não encontrado em TbdwDmModalidade.");
    		d.setModalidade(Constants.NAO_EXISTE_MODALIDADE + " " + d.getCdModalidade());
    	} else {
    		d.setModalidade(modalidade.getDsModalidade());
    	}
    });    
    return new PageImpl<>(listaModAplicDto, page, total(tblModAplicFilter));

  }

  public Long total(TblModAplicFilter tblModAplicFilter) {

    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TblModAplic> root = criteria.from(TblModAplic.class);

    Predicate[] predicate = criarRestricoes(tblModAplicFilter, builder, root);
    criteria.where(predicate);

    criteria.select(builder.count(root));

    return em.createQuery(criteria).getSingleResult();
  }

  private Predicate[] criarRestricoes(TblModAplicFilter tblModAplicFilter, CriteriaBuilder criteriaBuilder,
      Root<TblModAplic> root) {

    List<Predicate> predicates = new ArrayList<>();

    if (tblModAplicFilter.getCdApplication() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_APPLICATION), tblModAplicFilter.getCdApplication()));
    }

    if (tblModAplicFilter.getCdSeqModAplic() != null) {
        predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_SEQ_MOD_APLIC), tblModAplicFilter.getCdSeqModAplic()));
    }
    
    if (tblModAplicFilter.getCdModalidade() != null) {
        predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_MODALIDADE), tblModAplicFilter.getCdModalidade()));
      }

    
    return predicates.toArray(new Predicate[predicates.size()]);
  }
}
