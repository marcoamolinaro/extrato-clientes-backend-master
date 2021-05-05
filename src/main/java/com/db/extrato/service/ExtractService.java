package com.db.extrato.service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.extrato.controller.filter.ExtractFilter;
import com.db.extrato.dto.TexExtratoDTO;
import com.db.extrato.enums.ReportName;
import com.db.extrato.enums.ReportType;
import com.db.extrato.repository.extract.impl.ExtractRepositoryImpl;

import lombok.extern.java.Log;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class ExtractService {

  @Autowired
  private ExtractRepositoryImpl extractRepositoryImpl;
  
  public Page<TexExtratoDTO> findExtractPage(ExtractFilter extractFilter, ReportName reportName, ReportType reportType, Pageable page) {

	if (!ReportName.FULL.name().equals(reportName.name())) {
		extractFilter.setDtReferencia(formatDate(extractFilter));
	}

    List<TexExtratoDTO> data = extractRepositoryImpl.findExtractFilter(extractFilter,reportName, reportType, page);

    if (data.size() == 0) {
      throw new EmptyResultDataAccessException(1);
    }
    
    Page<TexExtratoDTO> pageReturn = new PageImpl<>(data, page, extractRepositoryImpl.total(extractFilter, reportType));

    return pageReturn;
  }

  public byte[] report(ExtractFilter extractFilter, ReportName reportName, ReportType reportType, Pageable page)
      throws ParseException {

    List<TexExtratoDTO> data = findDataToExtract(extractFilter, reportName, reportType, page);

    data.forEach(c -> {
      c.setVlOperacaoMn(getVlOperacaoMn(c));
      c.setVlOperacaoMe(getVlOperacaoMe(c));
    });
    
    for (TexExtratoDTO c : data) {
      c.setVlOperacaoMn(getVlOperacaoMn(c));
      c.setVlOperacaoMe(getVlOperacaoMe(c));
    }
    
    HashMap<String, Object> parameter = new HashMap<>();
    parameter.put("dbLogo", this.getClass().getResourceAsStream("/images/deutsche_bank_logo.png"));
    parameter.put("dbLogoSquare", this.getClass().getResourceAsStream("/images/deutsche_bank_square.png"));
    parameter.put("report_date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    parameter.put("REPORT_LOCALE", new Locale("pt", "BR")); 
    parameter.put("reportType", reportType.name().equals("CONSOLIDATE")? "CONSOLIDADO / CONSOLIDATE": "ANALITICO / ANALYTIC"); 

    try {
      InputStream inputStream = null;

      if (ReportName.MONTHLY_REPORT.name().equalsIgnoreCase(reportName.name())) {
        inputStream = this.getClass().getResourceAsStream("/relatorios/DBSA_monthly_report.jasper");
      } else {
        inputStream = this.getClass().getResourceAsStream("/relatorios/DBSA_annual_report.jasper");
      }

      JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameter,  new JRBeanCollectionDataSource(data));
      return JasperExportManager.exportReportToPdf(jasperPrint);
    } catch (JRException e) {
      log.severe(e.getMessage());
      throw new RuntimeException("Ocorreu um erro ao gerar o relatorio " + reportName.name() + "-" + reportType.name(), e);
    } catch (Exception e) {
      log.severe(e.getMessage());
      e.printStackTrace();
    }
    throw new RuntimeException("Ocorreu um erro ao gerar o relatorio " + reportName.name() + "-" + reportType.name());
  }

  private List<TexExtratoDTO> findDataToExtract(ExtractFilter extractFilter, ReportName reportName,
      ReportType reportType, Pageable page) throws ParseException {

    extractFilter.setDtReferencia(formatDate(extractFilter));

    List<TexExtratoDTO> data = extractRepositoryImpl.findExtractReport(extractFilter, reportName, reportType, page);

    if (data.size() == 0) {
      throw new EmptyResultDataAccessException(1);
    }

    return data;
  }


  private String formatDate(ExtractFilter extractFilter) {
    String referenceDateParam = extractFilter.getDtReferencia() != null ? extractFilter.getDtReferencia()
        : LocalDate.now().toString();

    LocalDate referenceDate = LocalDate.parse(referenceDateParam);

    Month month = referenceDate.getMonth();

    int year = referenceDate.getYear();
    return month.getValue() > 10 ? (month.getValue() + "-" + year) : ("0" + month.getValue()) + "-" + year;
  }

  public String getReferenceDate() {
    return new SimpleDateFormat("yyyy-MM-dd").format(extractRepositoryImpl.getRefereceDate());
  }
  
	public TexExtratoDTO findExtract(Long cdEmpresa, Long cdCliente, Long cdModalidade, String cdOperOrigem) {
		ExtractFilter filter = new ExtractFilter();
		filter.setCdEmpresa(cdEmpresa.toString());
		filter.setCdCliente(cdCliente.toString());
		filter.setCdModalidade(cdModalidade.toString());
		filter.setCdOperOrigem(cdOperOrigem);
		List<TexExtratoDTO> data = extractRepositoryImpl.findExtractFilter(filter, ReportName.FULL, ReportType.ANALYTIC, PageRequest.of(0, 1));
		if (data.size() == 0) {
	      throw new EmptyResultDataAccessException(1);
	    }
		return data.get(0);
	}
	
	public Double getVlOperacaoMn(TexExtratoDTO c) {
      
      if (c.getDisplaySldMn().equals("N") && c.getDisplaySldMe().equals("S") && c.getCdMoeda().equals(80)){
          return formatDecimal(c.getVlOperacaoMn());
      }else{
          if (c.getDisplaySldMn().equals("S")){
              if (c.getCdModalidade().equals(59L) && c.getCdMoeda().equals(80)){
                  return formatDecimal(c.getVlOperacaoMn());
              }
              else if (c.getCdModalidade().equals(47L) || !isVlOperacaoMnEmpty(c)){
                  return formatDecimal(c.getVlOperacaoMn());
              }
              else if (   c.getCdMoeda().equals(80) 
                      && (c.getCdModalidade().equals(172L) || c.getCdModalidade().equals(157L) || 
                          c.getCdModalidade().equals(61L)  || c.getCdModalidade().equals(719L) ||
                          c.getCdModalidade().equals(720L) || c.getCdModalidade().equals(722L) ||
                          c.getCdModalidade().equals(723L) || c.getCdModalidade().equals(724L))){
                  return formatDecimal(c.getVlOperacaoMn());
              }
          }
      }
      return 0.0;
  }
    
    public Double getVlOperacaoMe(TexExtratoDTO c) {
      if ( (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(172L)) ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(157L)) ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(61L))  ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(719L)) ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(720L)) ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(722L)) ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(723L)) ||
           (!c.getCdMoeda().equals(80) && !c.getCdModalidade().equals(724L)))
          {
          if (c.getDisplaySldMe().equals("S")){
              if (c.getCdModalidade().equals(47L) || !isVlOperacaoMeEmpty(c)){
                  return formatDecimal(c.getVlOperacaoMe());
              }
          }
      }
      
      return 0.0;
  }
    
    public boolean isVlOperacaoMnEmpty(TexExtratoDTO c){
      if (c.getVlOperacaoMn() == null){
          return true;
      }
      
      if (c.getVlOperacaoMn().equals(0D)){
          return true;
      }
      else{
          return false;
      }
  }

    public boolean isVlOperacaoMeEmpty(TexExtratoDTO c){
      if (c.getVlOperacaoMe() == null){
          return true;
      }
      
      if (c.getVlOperacaoMe() == 0){
          return true;
      }
      else{
          return false;
      }
  }

    private Double formatDecimal(Double decimal){
      
      DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US); 
      decimalFormat.setParseBigDecimal(true);
      
      decimalFormat.setMinimumFractionDigits(2);
      decimalFormat.setMaximumFractionDigits(2);
      decimalFormat.setDecimalSeparatorAlwaysShown(true);
      
      if (decimal == null || decimal == 0){
          return null;
      }
         return decimal; 

  }

}
