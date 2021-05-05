package com.db.extrato.repository.extract;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.db.extrato.domain.extract.ClientExtractManagement;
import com.db.extrato.domain.extract.ClientExtractManagementId;

@Repository
public interface ClientExtractManagementRepository extends JpaRepository<ClientExtractManagement, Long>{
	
	@Transactional(readOnly=true)
	ClientExtractManagement findByClientExtractManagementId(ClientExtractManagementId id);
		
	@Transactional(readOnly=true)
	List<ClientExtractManagement> findByClientExtractManagementIdCdCliBr(Long cdCliBr);
	
	@Transactional
	void deleteByClientExtractManagementIdCdCliBr(Long cdCliBr);
}
