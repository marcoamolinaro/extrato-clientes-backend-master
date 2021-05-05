package com.db.extrato;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExtratoApplicationTests {
/*
	@Test
	void contextLoads() {
	}

	@Autowired
	private ExtractService extractRepositoryImpl;

	@Test
	public void shouldReturnExtractsFiltered() {
		ExtractFilter extractFilter = new ExtractFilter();
		extractFilter.setCdEmpresa("101101");
		extractFilter.setDtReferencia("2020-04-01");
		Pageable page = PageRequest.of(1, 20);
		Page<TexExtratoDTO> extratos = extractRepositoryImpl.findExtractPage(extractFilter, ReportName.MONTHLY_REPORT, ReportType.CONSOLIDATE, page);
		
		extratos.forEach(ext -> {
			assertThat("101101").isEqualTo("101101");
		});
		
	}
	
	@Test
	public void shouldReturnEmptyResultDataAccessException() {
		ExtractFilter extractFilter = new ExtractFilter();
		extractFilter.setCdCliente("1673");
		Pageable page = PageRequest.of(1, 20);
		extractFilter.setDtReferencia("2020-04-01");

		assertThatExceptionOfType(EmptyResultDataAccessException.class)
						.isThrownBy(() -> {
							extractRepositoryImpl.findExtractPage(extractFilter, ReportName.MONTHLY_REPORT, ReportType.CONSOLIDATE, page);
						}).withMessage("Incorrect result size: expected 1, actual 0");
		
	}
*/
}
