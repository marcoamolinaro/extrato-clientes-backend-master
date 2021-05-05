package com.db.extrato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.extrato.domain.extract.ExtractLogReport;
import com.db.extrato.dto.ExtractLogReportDTO;
import com.db.extrato.repository.extract.ExtractLogReportRepository;

@Service
public class ExtractLogReportService {
	
	@Autowired
	private ExtractLogReportRepository repository;

	public ExtractLogReport insert(ExtractLogReportDTO dto) {
		ExtractLogReport extractLogReport = new ExtractLogReport();
		
		extractLogReport.setLogId(null);
		extractLogReport.setAgTipo(dto.getAgTipo());
		extractLogReport.setCdCli(dto.getCdCli());
		extractLogReport.setDescricao(dto.getDescricao());
		extractLogReport.setDtLog(dto.getDtLog());
		extractLogReport.setExecucao(dto.getExecucao());
		extractLogReport.setFilename(dto.getFilename());
		extractLogReport.setNmCli(dto.getNmCli());
		extractLogReport.setStatus(dto.getStatus());
		
		repository.save(extractLogReport);
		
		return extractLogReport;
	}
}
