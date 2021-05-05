package com.db.extrato.repository.extract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.extrato.domain.extract.TbdwDmPessoa;

@Repository
public interface TbdwDmPessoaRepository extends JpaRepository<TbdwDmPessoa, Long>{
	
	@Transactional(readOnly=true)
	TbdwDmPessoa findByCdPessoa(Long cdPessoa); 
}
