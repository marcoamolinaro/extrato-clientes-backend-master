package com.db.extrato.repository.extract;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.extrato.domain.extract.TexEntratoId;
import com.db.extrato.domain.extract.TexExtrato;

public interface TexExtratoRepository extends JpaRepository<TexExtrato, TexEntratoId> {

}
