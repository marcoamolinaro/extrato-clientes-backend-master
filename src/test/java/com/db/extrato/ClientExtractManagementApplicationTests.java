package com.db.extrato;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientExtractManagementApplicationTests {
/*
	@Test
	void contextLoads() {
	}

	@Autowired
	private ClientExtractManagementService service;

	@Test
	public void shouldReturnClientExtractManagementIsInExtract() {
		TbdwDmPessoaFilter filter = new TbdwDmPessoaFilter();
		filter.setCdPessoa(1166L);
		Pageable page = PageRequest.of(0, 10);
		Page<ClientExtractManagementDTO> clients = service.getGridExtratoDerivativoManutencao(filter, page);
		
		clients.forEach(c -> {
			assertThat(true).isEqualTo(c.getIsInsertedOnExtract());
		});
		
	}
	
	@Test
	public void shouldReturnClientExtractManagementIsNotExtract() {
		TbdwDmPessoaFilter filter = new TbdwDmPessoaFilter();
		filter.setCdPessoa(1188L);
		Pageable page = PageRequest.of(0, 10);
		Page<ClientExtractManagementDTO> clients = service.getGridExtratoDerivativoManutencao(filter, page);
		
		clients.forEach(c -> {
			assertThat(false).isEqualTo(c.getIsInsertedOnExtract());
		});
		
	}
	
	@Test
	public void shouldReturnClientExtractManagementIsActive() {
		TbdwDmPessoaFilter filter = new TbdwDmPessoaFilter();
		filter.setCdPessoa(10131L);
		Pageable page = PageRequest.of(0, 10);
		Page<ClientExtractManagementDTO> clients = service.getGridExtratoDerivativoManutencao(filter, page);
		
		clients.forEach(c -> {
			assertThat("A").isEqualTo(c.getCdSitPessoa());
		});
		
	}

	@Test
	public void shouldReturnClientExtractManagementIsBloqued() {
		TbdwDmPessoaFilter filter = new TbdwDmPessoaFilter();
		filter.setCdPessoa(11977L);
		Pageable page = PageRequest.of(0, 10);
		Page<ClientExtractManagementDTO> clients = service.getGridExtratoDerivativoManutencao(filter, page);
		
		clients.forEach(c -> {
			assertThat("B").isEqualTo(c.getCdSitPessoa());
		});
	}		
	
	@Test
	public void shouldReturnClientExtractManagementOnlyActive() {
		TbdwDmPessoaFilter filter = new TbdwDmPessoaFilter();
		filter.setCodSitPessoaAtiva(Constants.CLIENTE_ATIVO);
		filter.setCodSitPessoaBloqueada(null);
		Pageable page = PageRequest.of(0, 10);
		Page<ClientExtractManagementDTO> clients = service.getGridExtratoDerivativoManutencao(filter, page);
		
		clients.forEach(c -> {
			assertThat("A").isEqualTo(c.getCdSitPessoa());
		});
	}		
*/
}
