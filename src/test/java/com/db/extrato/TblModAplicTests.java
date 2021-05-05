package com.db.extrato;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TblModAplicTests {
	/*
	@Test
	void contextLoads() {
	}

	@Autowired
	private TblModAplicRepositoryImpl repositoryImpl;
	
	@Autowired
	private TblModAplicRepository repository;
	
	@Autowired
	private TblModAplicService service;
	
	@Test
	public void shouldReturnTblModAplicAll() {
		TblModAplicFilter filter = new TblModAplicFilter();
		Pageable page = PageRequest.of(0, 10);
		Page<TblModAplicDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
		
		List<TblModAplicDTO> contents = dtos.getContent();
		
		contents.forEach(c -> {
			assertNotNull(c.getCdApplication());
		});
		
	}

	@Test
	public void shouldReturnTblModAplicByCdApplicatpionParamerizada() {
		TblModAplicFilter filter = new TblModAplicFilter();
		filter.setCdApplication(1L);
		Pageable page = PageRequest.of(0, 1);
		Page<TblModAplicDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
			
		List<TblModAplicDTO> contents = dtos.getContent();

		contents.forEach(c -> {
			assertNotNull(c.getCdApplication());
		});
	}

	@Test
	public void shouldReturnTblModAplicByCdModaliadde() {
		TblModAplicFilter filter = new TblModAplicFilter();
		filter.setCdModalidade(67L);
		Pageable page = PageRequest.of(0, 1);
		Page<TblModAplicDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
			
		List<TblModAplicDTO> contents = dtos.getContent();
		
		contents.forEach(c -> {
			assertNotNull(c.getCdApplication());
		});
	}
	
	@Test
	public void shouldReturnTblModAplicByCdSeqModAplic() {
		TblModAplicFilter filter = new TblModAplicFilter();
		filter.setCdSeqModAplic(6L);;
		Pageable page = PageRequest.of(0, 1);
		Page<TblModAplicDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
			
		List<TblModAplicDTO> contents = dtos.getContent();

		contents.forEach(c -> {
			assertNotNull(c.getCdApplication());
		});
	}
	
	@Test
	public void shouldNotReturnTblModAplicByCdSeqModAplic() {
		TblModAplicFilter filter = new TblModAplicFilter();
		filter.setCdSeqModAplic(0L);;
		Pageable page = PageRequest.of(0, 1);
		Page<TblModAplicDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
		
		List<TblModAplicDTO> contents = dtos.getContent();
		
		contents.forEach(c -> {
			assertNotNull(c.getCdApplication());
		});
	}
	
	@Test
	public void shouldSaveAndDeleteModAplic() {
		TblModAplicDTO dto = new TblModAplicDTO();
		
		dto.setCdApplication(1L);
		dto.setCdGarantia(null);
		dto.setCdModalidade(56L);
		dto.setCdSeqModAplic(null);
		dto.setCdSisOrig(null);
		dto.setCgModulo(null);
		dto.setCgSistema(null);
		dto.setDisplayMe("S");
		dto.setDisplayMn("S");
		dto.setDisplayQtde(null);
		dto.setDsNegocio("TESTE 1");
		dto.setDsNegocioIngles("TEST 1");
		dto.setDsObn(null);
		dto.setCgModulo(null);
		dto.setNmIndexador(null);
		dto.setIiTipoContabil(null);
		
		TblModAplic tblModAplic;
		
		try {
			tblModAplic =  service.insert(dto);
		} catch (Exception e) {
			tblModAplic = null;
		}

		assertNotNull(tblModAplic);		
		
		List<TblModAplic> lista = repository.findAll();
		
		System.out.println("tblModAplic codSeqModAplic " + lista.get(lista.size()-1).getCdSeqModAplic());
		
		Boolean deletou = false;

		try {
			service.excluir(lista.get(lista.size()-1).getCdSeqModAplic());
			
			deletou = true;
		} catch (Exception e) {
			System.out.println("Errou aqui " + lista.get(lista.size()-1).getCdSeqModAplic());
		}

		assertThat(deletou == true);
		
	}
	
	@Test
	public void shouldUpdateModAplic() throws ObjectNotFoundException {
		TblModAplicDTO dto = new TblModAplicDTO();
				
		List<TblModAplic> lista = repository.findAll();
		
		System.out.println("TESTE ALTERACAO tblModAplic codSeqModAplic " + lista.get(lista.size()-1).getCdSeqModAplic());
		
		TblModAplic tblModAplic = service.find(lista.get(lista.size()-1).getCdSeqModAplic());
		
		System.out.println("TESTE ALTERACAO tblModAplic " + tblModAplic.toString());
		
		dto.setCdApplication(tblModAplic.getCdApplication());
		dto.setCdGarantia(tblModAplic.getCdGarantia());
		dto.setCdModalidade(tblModAplic.getCdModalidade());
		dto.setCdSeqModAplic(tblModAplic.getCdSeqModAplic());
		dto.setCdSisOrig(tblModAplic.getCdSisOrig());
		dto.setCgModulo(tblModAplic.getCgModulo());
		dto.setCgSistema(tblModAplic.getCgSistema());
		dto.setDisplayMe(tblModAplic.getDisplayMe());
		dto.setDisplayMn(tblModAplic.getDisplayMn());
		dto.setDisplayQtde(tblModAplic.getDisplayQtde());
		dto.setDsNegocio("TESTE DE ALTERACAO");
		dto.setDsNegocioIngles("UPDATE TEST");
		dto.setDsObn(tblModAplic.getDsObn());
		dto.setNmIndexador(tblModAplic.getDsNegocioIngles());
		dto.setIiTipoContabil(tblModAplic.getIiTipoContabil());
				
		Boolean alterou = false;

		try {
			service.alterar(dto, dto.getCdSeqModAplic());
			
			alterou = true;
		} catch (Exception e) {
			System.out.println("Errou aqui " + lista.get(lista.size()-1).getCdSeqModAplic());
		}

		assertThat(alterou == true);
		
	}
	*/
	
}
