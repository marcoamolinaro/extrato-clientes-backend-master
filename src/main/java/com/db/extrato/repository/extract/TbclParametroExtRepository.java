package com.db.extrato.repository.extract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.extrato.domain.extract.TbclParametroExt;

@Repository
public interface TbclParametroExtRepository extends JpaRepository<TbclParametroExt, Long> {

  
}
