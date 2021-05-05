package com.db.extrato.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.TblModAplic;
import com.db.extrato.dto.TblModAplicDTO;
import com.db.extrato.repository.extract.TblModAplicRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class TblModAplicService {
	
	@Autowired
	private TblModAplicRepository repository;

	public TblModAplic insert(TblModAplicDTO dto) {
		TblModAplic tblModAplic = new TblModAplic();	
		
		tblModAplic.setCdApplication(dto.getCdApplication());
		tblModAplic.setCdGarantia(dto.getCdGarantia());
		tblModAplic.setCdModalidade(dto.getCdModalidade());
		tblModAplic.setCdSeqModAplic(null);
		tblModAplic.setCdSisOrig(dto.getCdSisOrig());
		tblModAplic.setCgModulo(dto.getCgModulo());
		tblModAplic.setCgSistema(dto.getCgSistema());
		tblModAplic.setDisplayMe(dto.getDisplayMe());
		tblModAplic.setDisplayMn(dto.getDisplayMn());
		tblModAplic.setDisplayQtde(dto.getDisplayQtde());
		tblModAplic.setDsNegocio(dto.getDsNegocio());
		tblModAplic.setDsNegocioIngles(dto.getDsNegocioIngles());
		tblModAplic.setDsObn(dto.getDsObn());
		tblModAplic.setIiTipoContabil(dto.getIiTipoContabil());
		tblModAplic.setNmIndexador(dto.getNmIndexador());

		repository.saveAndFlush(tblModAplic);
		
		return tblModAplic;
	}
	
	public TblModAplic find(Long id) throws ObjectNotFoundException {
		Optional<TblModAplic> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + TblModAplic.class.getName()));
	}
	
	public void excluir(Long id) throws ObjectNotFoundException {
		find(id);
		
		repository.deleteById(id);
		
	}
	
	public void alterar(TblModAplicDTO dto, Long id) throws ObjectNotFoundException {
		System.out.println("ALTERAR tblModAplic " + dto.getCdSeqModAplic());
		System.out.println("ALTERAR id " + id);
		
		TblModAplic tblModAplic = find(id);
		
		tblModAplic.setCdApplication(dto.getCdApplication());
		tblModAplic.setCdGarantia(dto.getCdGarantia());
		tblModAplic.setCdModalidade(dto.getCdModalidade());
		tblModAplic.setCdSeqModAplic(dto.getCdSeqModAplic());
		tblModAplic.setCdSisOrig(dto.getCdSisOrig());
		tblModAplic.setCgModulo(dto.getCgModulo());
		tblModAplic.setCgSistema(dto.getCgSistema());
		tblModAplic.setDisplayMe(dto.getDisplayMe());
		tblModAplic.setDisplayMn(dto.getDisplayMn());
		tblModAplic.setDisplayQtde(dto.getDisplayQtde());
		tblModAplic.setDsNegocio(dto.getDsNegocio());
		tblModAplic.setDsNegocioIngles(dto.getDsNegocioIngles());
		tblModAplic.setDsObn(dto.getDsObn());
		tblModAplic.setIiTipoContabil(dto.getIiTipoContabil());
		tblModAplic.setNmIndexador(dto.getNmIndexador());
		
		System.out.println("ALTERAR tblModAplic " + tblModAplic.toString());
		
		repository.save(tblModAplic);
	}
	
}
