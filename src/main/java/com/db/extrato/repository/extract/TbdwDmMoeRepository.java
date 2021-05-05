package com.db.extrato.repository.extract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.extrato.domain.extract.TbdwDmMoe;

public interface TbdwDmMoeRepository extends JpaRepository<TbdwDmMoe, Long> {

	public List<TbdwDmMoe> findByDtExclusaoPtaxIsNullOrderByCdMoedaIsoAsc();
	
}
