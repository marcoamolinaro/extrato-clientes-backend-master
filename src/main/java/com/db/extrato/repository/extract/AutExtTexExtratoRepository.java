package com.db.extrato.repository.extract;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.extrato.domain.extract.AutExtTexExtrato;
import com.db.extrato.enums.AutStatus;

public interface AutExtTexExtratoRepository extends JpaRepository<AutExtTexExtrato, Long> {

	public boolean existsByCdEmpresaAndCdClienteAndCdModalidadeAndCdOperOrigemAndAutStatus(Long cdEmpresa, Long cdCliente, Long cdModalidade, String cdOperOrigem, AutStatus autStatus);
	
}
