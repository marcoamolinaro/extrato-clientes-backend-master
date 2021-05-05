package com.db.extrato.repository.extract;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.db.extrato.domain.extract.TbdwDmModalidade;
import com.db.extrato.dto.ProdDTO;

@Repository
public interface TbdwDmModalidadeRepository extends JpaRepository<TbdwDmModalidade, Long>{

  @Query("SELECT m FROM TbdwDmModalidade m WHERE str(m.cdModalidade) LIKE %?1% OR m.dsModalidade LIKE %?1% ORDER BY m.dsModalidade")
  List<TbdwDmModalidade> findByCdModalidadeOrDsModalidade(String dsModalidade);
  
  List<TbdwDmModalidade> findByOrderByDsModalidadeAsc();

  TbdwDmModalidade findByCdModalidade(Long cdModalidade);

  @Query("SELECT DISTINCT new com.db.extrato.dto.ProdDTO(m.cdProduto, m.dsProduto) FROM TbdwDmModalidade m WHERE m.cdProduto = ?1")
  Optional<ProdDTO> findProdById(Long cdProduto);
  
  @Query("SELECT DISTINCT new com.db.extrato.dto.ProdDTO(m.cdProduto, m.dsProduto) FROM TbdwDmModalidade m ORDER BY m.dsProduto")
  List<ProdDTO> findAllProdOrderBydsProduto();
  
  List<TbdwDmModalidade> findAllByCdProdutoOrderByDsModalidade(Long cdProduto);
  
}
