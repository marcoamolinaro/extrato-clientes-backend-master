package com.db.extrato.repository.extract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.db.extrato.domain.extract.TbdwDmEmp;

@Repository
public interface TbdwDmEmpRepository extends JpaRepository<TbdwDmEmp, Long>{

  @Query("SELECT e FROM TbdwDmEmp e WHERE e.nomeEmpresa LIKE %:nomeEmpresa% ORDER BY e.nomeEmpresa")
  List<TbdwDmEmp> findByName(String nomeEmpresa);
  
  List<TbdwDmEmp> findByOrderByNomeEmpresaAsc();
  
}
