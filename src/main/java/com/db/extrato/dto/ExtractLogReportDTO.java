package com.db.extrato.dto;

import java.io.Serializable;
import java.util.Date;

import com.db.extrato.domain.extract.ExtractLogReport;
import com.db.extrato.enums.ReportExecution;
import com.db.extrato.enums.ReportStatus;

import lombok.Data;

@Data
public class ExtractLogReportDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long logId;
	
	private Long cdCli;
	
	private String nmCli;
	
	private String agTipo;
	
	private ReportExecution execucao;
	
	private ReportStatus status;
	
	private String descricao;
	
	private String filename;
	
	private Date dtLog;	
	
	public static ExtractLogReportDTO convertDto(ExtractLogReport log) {
		ExtractLogReportDTO dto = new ExtractLogReportDTO();
		
		dto.setLogId(log.getLogId());
		dto.setCdCli(log.getCdCli());
		dto.setNmCli(log.getNmCli());
		dto.setAgTipo(log.getAgTipo());
		dto.setExecucao(log.getExecucao());
		dto.setStatus(log.getStatus());
		dto.setDescricao(log.getDescricao());
		dto.setFilename(log.getFilename());
		dto.setDtLog(log.getDtLog());
		
	    return dto;
	}
}
