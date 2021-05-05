package com.db.extrato;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExtractLogReportTests {
/*
	@Test
	void contextLoads() {
	}

	@Autowired
	private ExtractLogReportRepositoryImpl repositoryImpl;
		
	@Test
	public void shouldReturnExtractLogReportAll() {
		ExtractLogReportFilter filter = new ExtractLogReportFilter();
		Pageable page = PageRequest.of(0, 10);
		Page<ExtractLogReportDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
		
		List<ExtractLogReportDTO> contents = dtos.getContent();
		
		assertNotNull(contents);		
	}
	
	
	@Test
	public void shouldReturnExtractLogReportByDate() {
		ExtractLogReportFilter filter = new ExtractLogReportFilter();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
		String dateInString = "2015-06-22";
		Date date;
		try {
			date = formatter.parse(dateInString);
			filter.setDtLog(date);
			Pageable page = PageRequest.of(0, 10);
			Page<ExtractLogReportDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
			
			List<ExtractLogReportDTO> contents = dtos.getContent();
			
			assertThat(!contents.isEmpty());
			
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	
	@Test
	public void shouldNotReturnExtractLogReportByDate() {
		ExtractLogReportFilter filter = new ExtractLogReportFilter();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
		String dateInString = "2014-01-01";
		Date date;
		try {
			date = formatter.parse(dateInString);
			filter.setDtLog(date);
			Pageable page = PageRequest.of(0, 10);
			Page<ExtractLogReportDTO> dtos = repositoryImpl.pesquisarPage(filter, page);
			
			List<ExtractLogReportDTO> contents = dtos.getContent();
			
			assertThat(contents.isEmpty());
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}	
	}	
*/
}
