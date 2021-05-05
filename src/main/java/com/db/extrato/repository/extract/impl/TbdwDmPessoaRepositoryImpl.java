package com.db.extrato.repository.extract.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.TbdwDmPessoaFilter;
import com.db.extrato.domain.extract.TbdwDmPessoa;
import com.db.extrato.dto.TbdwDmPessoaDTO;
import com.db.extrato.util.Constants;

@Repository
public class TbdwDmPessoaRepositoryImpl {

  @PersistenceContext
  private EntityManager em;
  
  public Page<TbdwDmPessoaDTO> pesquisarPage(TbdwDmPessoaFilter tbdwDmPessoaFilter, Pageable page) {

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<TbdwDmPessoa> criteria = criteriaBuilder.createQuery(TbdwDmPessoa.class);
    Root<TbdwDmPessoa> root = criteria.from(TbdwDmPessoa.class);
    
    Predicate[] predicates = criarRestricoes(tbdwDmPessoaFilter, criteriaBuilder, root);
    criteria.where(predicates);
    TypedQuery<TbdwDmPessoa> query = em.createQuery(criteria);
    List<TbdwDmPessoaDTO> listaPessoaDto = new ArrayList<>();
    
    int paginaAtual = page.getPageNumber();
    int totalRegistrosPOrOagina = page.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPOrOagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPOrOagina); 
    
    List<TbdwDmPessoa> pessoa = query.getResultList();
    
    pessoa.forEach(p -> {
      listaPessoaDto.add(TbdwDmPessoaDTO.convertDto(p));
    });
    
    return new PageImpl<>(listaPessoaDto, page, total(tbdwDmPessoaFilter));

  }

  public Long total(TbdwDmPessoaFilter tbdwDmPessoaFilter) {

    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TbdwDmPessoa> root = criteria.from(TbdwDmPessoa.class);

    Predicate[] predicate = criarRestricoes(tbdwDmPessoaFilter, builder, root);
    criteria.where(predicate);

    criteria.select(builder.count(root));

    return em.createQuery(criteria).getSingleResult();
  }

  private Predicate[] criarRestricoes(TbdwDmPessoaFilter tbdwDmPessoaFilter, CriteriaBuilder criteriaBuilder,
      Root<TbdwDmPessoa> root) {

    List<Predicate> predicates = new ArrayList<>();

    if (tbdwDmPessoaFilter.getCdPessoa() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_PESSOA), tbdwDmPessoaFilter.getCdPessoa()));
    }

    if (tbdwDmPessoaFilter.getNmPessoa() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.ATTR_NM_PESSOA)), "%" + tbdwDmPessoaFilter.getNmPessoa().toUpperCase() + "%"));
    }

    if (tbdwDmPessoaFilter.getNmPessoaAbrev() != null) {
      predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.ATTR_NM_PESSOA_ABREV)), "%" + tbdwDmPessoaFilter.getNmPessoaAbrev().toUpperCase() + "%"));
    }

    if (tbdwDmPessoaFilter.getCdGcgCpf() != null) {
      predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_CD_CGC_CPF), tbdwDmPessoaFilter.getCdGcgCpf()));
    }
    
    if (tbdwDmPessoaFilter.getCodSitPessoaBloqueada() != null) {
    	In<String> inClause = criteriaBuilder.in(root.get(Constants.ATTR_COD_SIT_PESSOA));
    	inClause.value(Constants.CLIENTE_ATIVO);
       	inClause.value(Constants.CLIENTE_BLOQUEADO);
        predicates.add(inClause);   	
     } else if (tbdwDmPessoaFilter.getCodSitPessoaBloqueada() == null || tbdwDmPessoaFilter.getCodSitPessoaBloqueada().isEmpty()) {
         predicates.add(criteriaBuilder.equal(root.get(Constants.ATTR_COD_SIT_PESSOA), tbdwDmPessoaFilter.getCodSitPessoaAtiva()));   	
     }
    
    return predicates.toArray(new Predicate[predicates.size()]);
  }
}
