package com.db.extrato.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.text.MaskFormatter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.extrato.domain.extract.ClientExtractManagement;
import com.db.extrato.domain.extract.TbdwDmPessoa;
import com.db.extrato.dto.CdbPenaltyDTO;
import com.db.extrato.dto.CdbPenaltyXlsDTO;
import com.db.extrato.dto.ClientExtractManagementDTO;
import com.db.extrato.dto.ExtractLogReportDTO;
import com.db.extrato.dto.NdfDTO;
import com.db.extrato.dto.NdfXlsDTO;
import com.db.extrato.dto.OpcaoFlexivelDTO;
import com.db.extrato.dto.OpcaoFlexivelXlsDTO;
import com.db.extrato.dto.PosicaoSwapDTO;
import com.db.extrato.dto.RendaFixaDTO;
import com.db.extrato.dto.RendaFixaXlsDTO;
import com.db.extrato.dto.SwapDTO;
import com.db.extrato.dto.SwapFluxoDTO;
import com.db.extrato.dto.SwapFluxoXlsDTO;
import com.db.extrato.dto.SwapXlsDTO;
import com.db.extrato.dto.TermosDTO;
import com.db.extrato.dto.TermosXlsDTO;
import com.db.extrato.enums.DerivativeReport;
import com.db.extrato.enums.ReportExecution;
import com.db.extrato.enums.ReportStatus;
import com.db.extrato.enums.VrDiaSemana;
import com.db.extrato.enums.VrEmailFlag;
import com.db.extrato.enums.VrHorario;
import com.db.extrato.enums.VrPeriodo;
import com.db.extrato.exception.DerivativesException;
import com.db.extrato.repository.derivatives.DerivativesRepository;
import com.db.extrato.repository.extract.ClientExtractManagementRepository;
import com.db.extrato.repository.extract.TbdwDmPessoaRepository;
import com.db.extrato.repository.extract.impl.ClientExtractManagementRepositoryImpl;
import com.db.extrato.repository.extract.impl.ReportTimeLimitRepositoryImpl;
import com.db.extrato.util.ReportUtil;
import com.db.extrato.util.mail.ExtractInterfaceMailSender;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Service
public class DerivativesService {

	@Autowired
	private DerivativesRepository derivativesRepository;
	
	@Autowired
	private TbdwDmPessoaRepository tbdwDmPessoaRepository;
	
	@Autowired
	private ExtractLogReportService extractLogReportService;
	
	@Autowired
	private ReportTimeLimitRepositoryImpl reportTimeLimitRepositoryImpl;
	
	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private ExtractInterfaceMailSender extractInterfaceMailSender;
	
	@Autowired
	private ClientExtractManagementRepository clientExtractManagementRepository;
	
	@Autowired
	private ClientExtractManagementRepositoryImpl clientExtractManagementRepositoryImpl;
	
	@Value("${com.db.extrato.extractclient.pdf.folder}")
	private String reportOutputFolder;
	
	@Value("${com.db.extrato.extractclient.local.folder}")
	private String localOutputFolder;
	
	public synchronized void generateScheduledJob(VrHorario horario) throws IOException, JRException {
		Calendar calendar = Calendar.getInstance();
		Date dataAtual = calendar.getTime();
		VrDiaSemana diaSemana = VrDiaSemana.getDiaSemana(calendar.get(Calendar.DAY_OF_WEEK));
		Integer ultimoDiaMes = 0;
		if(getLastDayOfMonth())
			ultimoDiaMes = 1;
		List<ClientExtractManagementDTO> clientes = clientExtractManagementRepositoryImpl.findClientesAgendamento(dataAtual, 1, horario, diaSemana.getVrDiaSemana(), ultimoDiaMes);
		Map<String, byte[]> reports = new HashMap<>(); 
		try {
			reports.putAll(scheluleReport("quartz", true, dataAtual, dataAtual, clientes, false));
		} catch(EmptyResultDataAccessException e) {}
		if(MapUtils.isNotEmpty(reports))
			extractInterfaceMailSender.sendMailToSuccessScheduledReport(horario);
	}
	
	public byte[] reportZip(String usuario, boolean isAgendamento, Date dataConsulta, Date dataGeracaoReport, List<Long> cdClientes, boolean pdf, boolean excel) throws IOException, JRException {
		Map<String, byte[]> reports = new HashMap<>();
		try {
			reports.putAll(report(usuario, isAgendamento, dataConsulta, dataGeracaoReport, cdClientes, pdf, excel, false));
		} catch(Exception e) {}
		if(MapUtils.isEmpty(reports))
			throw new EmptyResultDataAccessException("Não existem dados para essa data e cliente(s).", 1);
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zos = new ZipOutputStream(baos)) {
			for (Entry<String, byte[]> reporte : reports.entrySet()) {
				ZipEntry entry = new ZipEntry(reporte.getKey());
				entry.setSize(reporte.getValue().length);
				zos.putNextEntry(entry);
				zos.write(reporte.getValue());
			}
			zos.closeEntry();
			zos.close();
			return baos.toByteArray();
		}
	}
	
	public void reportEmail(String usuario, boolean isAgendamento, Date dataConsulta, Date dataGeracaoReport, List<Long> cdClientes) throws IOException, JRException {
		Map<String, byte[]> reports = new HashMap<>();
		try {
			reports.putAll(report(usuario, isAgendamento, dataConsulta, dataGeracaoReport, cdClientes, true, false, true));
		} catch(Exception e) {}
		if(MapUtils.isEmpty(reports))
			throw new EmptyResultDataAccessException("Não existem dados para essa data e cliente(s).", 1);
	}
	
      	public Map<String, byte[]> scheluleReport(String usuario, boolean isAgendamento, Date dataConsulta, Date dataGeracaoReport,  List<ClientExtractManagementDTO> schedules , boolean email) throws JRException {
      	     Map<String, byte[]> reports = new HashMap<>();
      	     for(ClientExtractManagementDTO schedule : schedules) {
      	         try {
      	           
                   boolean pdf = schedule.getVrTipoRelatorio() != null && (schedule.getVrTipoRelatorio().equals("PDF") || schedule.getVrTipoRelatorio().equals("AMBOS"));
                   boolean excel = schedule.getVrTipoRelatorio() != null && (schedule.getVrTipoRelatorio().equals("EXCEL") || schedule.getVrTipoRelatorio().equals("AMBOS"));

      	             reports.putAll(report(usuario, isAgendamento, dataConsulta, dataGeracaoReport, schedule.getCdCliente(), pdf, excel, email));
      	         } catch(Exception e) {}
      	     }
      	     if(MapUtils.isEmpty(reports))
      	         throw new EmptyResultDataAccessException("Não existem dados para essa data e cliente(s).", 1);
      	     return reports;
      	}
	
	public Map<String, byte[]> report(String usuario, boolean isAgendamento, Date dataConsulta, Date dataGeracaoReport, List<Long> cdClientes, boolean pdf, boolean excel, boolean email) throws JRException {
		Map<String, byte[]> reports = new HashMap<>();
		for(Long cdCliente : cdClientes) {
			try {
				reports.putAll(report(usuario, isAgendamento, dataConsulta, dataGeracaoReport, cdCliente, pdf, excel, email));
			} catch(Exception e) {}
		}
		if(MapUtils.isEmpty(reports))
			throw new EmptyResultDataAccessException("Não existem dados para essa data e cliente(s).", 1);
		return reports;
	}
	
	@Transactional(noRollbackFor = Exception.class)
	public Map<String, byte[]> report(String usuario, boolean isAgendamento, Date dataConsulta, Date dataGeracaoReport, Long cdCliente, boolean pdf, boolean excel, boolean email) {
		TbdwDmPessoa pessoa = null;
		if(checkUserisRunningReport(usuario, cdCliente))
			throw new DerivativesException("Relatório já está sendo executado.");
		reportTimeLimitRepositoryImpl.insertReportTimeLimit(usuario, cdCliente);
		
		try {
			pessoa = tbdwDmPessoaRepository.findByCdPessoa(cdCliente);
			if(pessoa == null)
				throw new EmptyResultDataAccessException("Não existem dados para essa cliente", 1);
			HashMap<String, Object> parameter = new HashMap<>();
			parameter.put("DATA",  dataConsulta);
		    parameter.put("report_date", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			parameter.put("DATA_STR", new SimpleDateFormat("dd/MM/yyyy").format(dataConsulta));
			parameter.putAll(addReportHeader(pessoa));
			parameter.putAll(getReportData(usuario, dataConsulta, pessoa));
			Map<String, byte[]> reportMap = new HashMap<>();
			if(pdf) {
				InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/derivativos/Extract_Derivatives_Report.jasper");
				parameter.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameter, new JREmptyDataSource());
				String nomeArquivoPdf = new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".pdf";
				reportMap.put(nomeArquivoPdf, JasperExportManager.exportReportToPdf(jasperPrint));
			}
			if(excel) {
				Map<String, byte[]> swapXlsx = convertSwapXlsx(parameter, dataConsulta, pessoa);
				if(swapXlsx != null)
					reportMap.putAll(swapXlsx);
				Map<String, byte[]> swapFluxoXlsx = convertSwapFluxoXlsx(parameter, dataConsulta, pessoa);
				if(swapFluxoXlsx != null)
					reportMap.putAll(swapFluxoXlsx);
				Map<String, byte[]> ndfXlsx = convertNdfXlsx(parameter, dataConsulta, pessoa);
				if(ndfXlsx != null)
					reportMap.putAll(ndfXlsx);
				Map<String, byte[]> opcaoFlexivelXlsx = convertOpcaoFlexivelXlsx(parameter, dataConsulta, pessoa);
				if(opcaoFlexivelXlsx != null)
					reportMap.putAll(opcaoFlexivelXlsx);
				Map<String, byte[]> termosXlsx = convertTermosXlsx(parameter, dataConsulta, pessoa);
				if(termosXlsx != null)
					reportMap.putAll(termosXlsx);
				Map<String, byte[]> rendaFixaXlsx = convertRendaFixaXlsx(parameter, dataConsulta, pessoa);
				if(rendaFixaXlsx != null)
					reportMap.putAll(rendaFixaXlsx);
				Map<String, byte[]> cdbPenaltyXlsx = convertCdpPenaltyXlsx(parameter, dataConsulta, pessoa);
				if(cdbPenaltyXlsx != null)
					reportMap.putAll(cdbPenaltyXlsx);
			}
			if(email) {
				for(Entry<String, byte[]> entry : reportMap.entrySet()) {
					String destinationReportPath = localOutputFolder;
					File path = new File(destinationReportPath);
					if(!path.exists())
						path.mkdirs();
					try(FileOutputStream outputStream = new FileOutputStream(destinationReportPath + File.separator + entry.getKey())) {
						outputStream.write(entry.getValue());
					}
				}
			}
			if(isAgendamento) {
				for(Entry<String, byte[]> entry : reportMap.entrySet()) {
					String destinationReportPath = reportOutputFolder;
					ClientExtractManagement cliente = clientExtractManagementRepository.findByClientExtractManagementIdCdCliBr(pessoa.getCdPessoa()).stream().findFirst().orElse(null);
					if(cliente != null && VrEmailFlag.NAO.getVrEmailFlag().equals(cliente.getVrEmailFlag()))
						destinationReportPath = localOutputFolder + File.separator + new SimpleDateFormat("yyyyMMdd").format(dataConsulta);
					File path = new File(destinationReportPath);
					if(!path.exists())
						path.mkdirs();
					try(FileOutputStream outputStream = new FileOutputStream(destinationReportPath + File.separator + entry.getKey())) {
						outputStream.write(entry.getValue());
					}
					String agTipo = VrPeriodo.valueOf(cliente.getClientExtractManagementId().getVrPeriodo()).getValue().concat(" ").concat(VrHorario.valueOf(cliente.getVrHorario()).getVrHorario());
					logReport(isAgendamento, cdCliente, entry.getKey(), pessoa, ReportStatus.SUCCESS, agTipo, "Cliente " + cdCliente + " Gerou Extrato Com Sucesso");
				}
			} else {
				for(Entry<String, byte[]> entry : reportMap.entrySet()) {
					logReport(isAgendamento, cdCliente, entry.getKey(), pessoa, ReportStatus.SUCCESS, null, "Cliente " + cdCliente + " Gerou Extrato Com Sucesso");
				}
			}
			return reportMap;
		} catch(Exception e) {
			if(e.getClass().equals(EmptyResultDataAccessException.class)) {
				logReport(isAgendamento, cdCliente, null, pessoa, ReportStatus.SUCCESS, null, "Cliente " + cdCliente + ". Não há dados para a data " + new SimpleDateFormat("yyyyMMdd").format(dataConsulta));
				throw new EmptyResultDataAccessException(StringUtils.isNotBlank(e.getLocalizedMessage()) ? e.getLocalizedMessage() : e.toString(), 1);
			} else {
				logReport(isAgendamento, cdCliente, null, pessoa, ReportStatus.ERROR, null, "Cliente " + cdCliente + " nao gerou o extrato . Erro:" + (StringUtils.isNotBlank(e.getLocalizedMessage()) ? e.getLocalizedMessage() : e.toString()));
				throw new DerivativesException(e.getMessage());
			}
		} finally {
			reportTimeLimitRepositoryImpl.deleteReportTimeLimit(usuario, cdCliente);
		}
	}
	
	public Map<String, byte[]> convertSwapXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaSwap") == null)
			return null;
		List<SwapDTO> swapList = ((List<?>) parameter.get("listaSwap"))
				.stream()
				.map(row -> SwapDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(swapList))
			return null;
		List<SwapXlsDTO> swapXlsList = new ArrayList<>();
		for(SwapDTO swap : swapList) {
			SwapXlsDTO bean = new SwapXlsDTO();
			bean.setNrContrato(swap.getNrContrato());
			bean.setVlrBase(Double.parseDouble(swap.getVlrBase()));
			bean.setDtInicio(swap.getDtInicio() != null ? DateFormatUtils.format(swap.getDtInicio(), "dd/MM/yyyy") : null);
			bean.setDtVcto(swap.getDtVcto() != null ? DateFormatUtils.format(swap.getDtVcto(), "dd/MM/yyyy") : null);
			bean.setIndexParte(swap.getIndexParte());
			bean.setIndexPartePerc(swap.getIndexPartePerc() != null ? Double.parseDouble(swap.getIndexPartePerc()) : null);
			bean.setTaxaParte252(swap.getTaxaParte252() != null ? Double.parseDouble(swap.getTaxaParte252()): null);
			bean.setTaxaParte360(swap.getTaxaParte360() != null ? Double.parseDouble(swap.getTaxaParte360()) : null);
			bean.setValorParte(swap.getValorParte() != null ? Double.parseDouble(swap.getValorParte()) : null);
			bean.setIndexContraParte(swap.getIndexContraParte());
			bean.setIndexContraPartePerc(swap.getIndexContraPartePerc() != null ? Double.parseDouble(swap.getIndexContraPartePerc()) : null);
			bean.setTaxaContraParte252(swap.getTaxaContraParte252() != null ? Double.parseDouble(swap.getTaxaContraParte252()) : null);
			bean.setTaxaContraParte360(swap.getTaxaContraParte360() != null ? Double.parseDouble(swap.getTaxaContraParte360()) : null);
			bean.setValorContraParte(swap.getValorContraParte() != null ? Double.parseDouble(swap.getValorContraParte()) : null);
			bean.setAjusteFiscal(swap.getAjusteFiscal() != null ? Double.parseDouble(swap.getAjusteFiscal()) : null);
			bean.setAjusteMTM(swap.getAjusteMTM() != null ? Double.parseDouble(swap.getAjusteMTM()) : null);			
			swapXlsList.add(bean);
		}
		parameter.put("listaSwapXLS", swapXlsList);
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "SW" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.SWAP_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}
	
	public Map<String, byte[]> convertSwapFluxoXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaSwapFluxo") == null)
			return null;
		List<SwapFluxoDTO> swapFluxoList = ((List<?>) parameter.get("listaSwapFluxo"))
				.stream()
				.map(row -> SwapFluxoDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(swapFluxoList))
			return null;
		List<SwapFluxoXlsDTO> swapFluxoXlsList = new ArrayList<>();
		for(SwapFluxoDTO swapFluxo : swapFluxoList) {
			SwapFluxoXlsDTO bean = new SwapFluxoXlsDTO();
			bean.setNrContrato(swapFluxo.getNrContrato());
			bean.setVlrBase(swapFluxo.getVlrBase() != null ? Double.parseDouble(swapFluxo.getVlrBase()): null);
			bean.setDtInicio(swapFluxo.getDtInicio());
			bean.setDtVcto(swapFluxo.getDtVcto());
			bean.setIndexParte(swapFluxo.getIndexParte());
			bean.setIndexPartePerc(swapFluxo.getIndexPartePerc() != null ? Double.parseDouble(swapFluxo.getIndexPartePerc()) : null);
			bean.setTaxaParte360(swapFluxo.getTaxaParte360() != null ? Double.parseDouble(swapFluxo.getTaxaParte360()) : null);
			bean.setValorParte(swapFluxo.getValorParte() != null ? Double.parseDouble(swapFluxo.getValorParte()) : null);
			bean.setIndexContraParte(swapFluxo.getIndexContraParte());
			bean.setIndexContraPartePerc(swapFluxo.getIndexContraPartePerc() != null ? Double.parseDouble(swapFluxo.getIndexContraPartePerc()) : null);
			bean.setTaxaContraParte360(swapFluxo.getTaxaContraParte360() != null ? Double.parseDouble(swapFluxo.getTaxaContraParte360()) : null);
			bean.setValorContraParte(swapFluxo.getValorContraParte() != null ? Double.parseDouble(swapFluxo.getValorContraParte()) : null);
			bean.setAjusteFiscal(swapFluxo.getAjusteFiscal() != null ? Double.parseDouble(swapFluxo.getAjusteFiscal()) : null);
			bean.setAjusteMTM(swapFluxo.getAjusteMTM() != null ? Double.parseDouble(swapFluxo.getAjusteMTM()) : null);
			bean.setIdOperacao(swapFluxo.getIdOperacao());
			bean.setNrParcela(swapFluxo.getNrParcela());
			bean.setTaxaParcela252(swapFluxo.getTaxaParcela252() != null ? Double.parseDouble(swapFluxo.getTaxaParcela252()) : null);
			bean.setTaxaParcela360(swapFluxo.getTaxaParcela360() != null ? Double.parseDouble(swapFluxo.getTaxaParcela360()) : null);
			bean.setDtInicioParcela(swapFluxo.getDtInicioParcela());
			bean.setDtVctoParcela(swapFluxo.getDtVctoParcela());
			swapFluxoXlsList.add(bean);
		}
		parameter.put("listaSwapFluxoXLS", swapFluxoXlsList);
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "SF" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.SWAP_FLUXO_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}
	
	public Map<String, byte[]> convertNdfXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaNDF") == null)
			return null;
		List<NdfDTO> ndfList = ((List<?>) parameter.get("listaNDF"))
				.stream()
				.map(row -> NdfDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(ndfList))
			return null;
		List<NdfXlsDTO> ndfXlsList = new ArrayList<>();
		for(NdfDTO ndf : ndfList) {
			NdfXlsDTO bean = new NdfXlsDTO();
			bean.setNrContrato(ndf.getNrContrato());
			bean.setVlrBase(ndf.getVlrBase() != null ? Double.parseDouble(ndf.getVlrBase()) : null);
			bean.setDtInicio(ndf.getDtInicio() != null ? DateFormatUtils.format(ndf.getDtInicio(), "dd/MM/yyyy") : null);
			bean.setDtVcto(ndf.getDtVcto() != null ? DateFormatUtils.format(ndf.getDtVcto(), "dd/MM/yyyy") : null);
			bean.setMoedaRef(ndf.getMoedaRef());
			bean.setCotTermo(ndf.getCotTermo() != null ? Double.parseDouble(ndf.getCotTermo()) : null);
			bean.setTaxPre252(ndf.getTaxPre252() != null ? Double.parseDouble(ndf.getTaxPre252()) : null);
			bean.setPosicao(ndf.getPosicao());
			bean.setFiscalParte(ndf.getFiscalParte() != null ? Double.parseDouble(ndf.getFiscalParte()) : null);
			bean.setFiscalContraParte(ndf.getFiscalContraParte() != null ? Double.parseDouble(ndf.getFiscalContraParte()) : null);
			bean.setAjusteFiscal(ndf.getAjusteFiscal() != null ? Double.parseDouble(ndf.getAjusteFiscal()) : null);
			bean.setAjusteMTM(ndf.getAjusteMTM() != null ? Double.parseDouble(ndf.getAjusteMTM()) : null);
			bean.setVlrNotional(ndf.getVlrNotional() != null ? Double.parseDouble(ndf.getVlrNotional()) : null);
			ndfXlsList.add(bean);
		}
		parameter.put("listaNDFXLS", ndfXlsList);
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "NDF" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.NDF_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}
	
	public Map<String, byte[]> convertOpcaoFlexivelXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaOpcoes") == null)
			return null;
		List<OpcaoFlexivelDTO> opcoesList = ((List<?>) parameter.get("listaOpcoes"))
				.stream()
				.map(row -> OpcaoFlexivelDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(opcoesList))
			return null;
		List<OpcaoFlexivelXlsDTO> opcoesCallList = new ArrayList<>();
		List<OpcaoFlexivelXlsDTO> opcoesPutList = new ArrayList<>();
		for(OpcaoFlexivelDTO opcao : opcoesList) {
			OpcaoFlexivelXlsDTO bean = new OpcaoFlexivelXlsDTO();
			bean.setTipoOpcao(opcao.getTipoOpcao());
			bean.setPosicao(opcao.getPosicao());
			bean.setMoeda(opcao.getMoeda());
			bean.setVlrBaseOrig(opcao.getVlrBaseOrig());
			bean.setVlrBaseAtual(opcao.getVlrBaseAtual());
			bean.setPremio(opcao.getPremio());
			bean.setDtPremio(opcao.getDtPremio() != null ? DateFormatUtils.format(opcao.getDtPremio(), "dd/MM/yyyy") : null);
			bean.setPrecoExercicio(opcao.getPrecoExercicio());
			bean.setDtReg(opcao.getDtReg() != null ? DateFormatUtils.format(opcao.getDtReg(), "dd/MM/yyyy") : null);
			bean.setDtVcto(opcao.getDtVcto() != null ? DateFormatUtils.format(opcao.getDtVcto(), "dd/MM/yyyy") : null);
			bean.setTipoExercicio(opcao.getTipoExercicio());
			bean.setDtFixing(opcao.getDtFixing() != null ? DateFormatUtils.format(opcao.getDtFixing(), "dd/MM/yyyy") : null);
			bean.setFonteInfo(opcao.getFonteInfo());
			bean.setLocalReg(opcao.getLocalReg());
			bean.setCodCetip(opcao.getCodCetip());
			bean.setValorMTM(opcao.getValorMTM());
			if(opcao.getTipoOpcao().equals("CALL") || opcao.getTipoOpcao().equals("call") || opcao.getTipoOpcao().equals("Call"))
				opcoesCallList.add(bean);
			if(opcao.getTipoOpcao().equals("PUT") || opcao.getTipoOpcao().equals("put") || opcao.getTipoOpcao().equals("Put"))
				opcoesCallList.add(bean);
		}
		parameter.put("listaOpcoesXLSCall", opcoesCallList);
		parameter.put("listaOpcoesXLSPut", opcoesPutList);
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "OP" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.OPCOES_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}

	public Map<String, byte[]> convertTermosXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaTermos") == null)
			return null;
		List<TermosDTO> termosList = ((List<?>) parameter.get("listaTermos"))
				.stream()
				.map(row -> TermosDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(termosList))
			return null;
		List<TermosXlsDTO> ndfXlsList = new ArrayList<>();
		for(TermosDTO termo : termosList) {
			TermosXlsDTO bean = new TermosXlsDTO();
			bean.setPosicao(termo.getPosicao());
			bean.setDataInicio(termo.getDataInicio() != null ? DateFormatUtils.format(termo.getDataInicio(), "dd/MM/yyyy") : null);
			bean.setDataVencto(termo.getDataVencto() != null ? DateFormatUtils.format(termo.getDataVencto(), "dd/MM/yyyy") : null);
			bean.setMercadoria(termo.getMercadoria());
			bean.setUnidadeNegociacao(termo.getUnidadeNegociacao());
			bean.setBolsaReferencia(termo.getBolsaReferencia());
			bean.setQuantidade(termo.getQuantidade() != null ? Double.parseDouble(termo.getQuantidade().toString()) : null);
			bean.setPrecoOperacao(termo.getPrecoOperacao());
			bean.setMoeda(termo.getMoeda());
			bean.setCotacaoSpot(termo.getCotacaoSpot());
			bean.setContrato(termo.getContrato());
			bean.setValorMTM(termo.getValorMTM());
			ndfXlsList.add(bean);
		}
		parameter.put("listaTermosXLS", ndfXlsList);
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "TM" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.TERMOS_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}
	
	public Map<String, byte[]> convertRendaFixaXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaRendaFixa") == null)
			return null;
		List<RendaFixaDTO> rendaFixaList = ((List<?>) parameter.get("listaRendaFixa"))
				.stream()
				.map(row -> RendaFixaDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(rendaFixaList))
			return null;
		List<RendaFixaXlsDTO> rendaFixaXlsList = new ArrayList<>();
		for(RendaFixaDTO rendaFixa : rendaFixaList) {
			RendaFixaXlsDTO bean = new RendaFixaXlsDTO();
			bean.setMercadoria(rendaFixa.getMercadoria());
			bean.setIndexador(rendaFixa.getIndexador());
			bean.setDtEmissao(rendaFixa.getDtEmissao() != null ? DateFormatUtils.format(rendaFixa.getDtEmissao(), "dd/MM/yyyy") : null);
			bean.setDtVcto(rendaFixa.getDtVcto() != null ? DateFormatUtils.format(rendaFixa.getDtVcto(), "dd/MM/yyyy") : null);
			bean.setVlrBase(rendaFixa.getVlrBase() != null ? Double.parseDouble(rendaFixa.getVlrBase()) : null);
			bean.setTaxaOpe(rendaFixa.getTaxaOpe() != null ? Double.parseDouble(rendaFixa.getTaxaOpe()) : null);
			bean.setTaxa252(rendaFixa.getTaxa252() != null ? Double.parseDouble(rendaFixa.getTaxa252()) : null);
			bean.setTaxa360(rendaFixa.getTaxa360() != null ? Double.parseDouble(rendaFixa.getTaxa360()) : null);
			bean.setPerc(rendaFixa.getPerc() != null ? Double.parseDouble(rendaFixa.getPerc()) : null);
			bean.setQtde(rendaFixa.getQtde() != null ? Double.parseDouble(rendaFixa.getQtde()) : null);
			bean.setAjusteFiscal(rendaFixa.getAjusteFiscal() != null ? Double.parseDouble(rendaFixa.getAjusteFiscal()) : null);
			bean.setAjusteMTM(rendaFixa.getAjusteMTM() != null ? Double.parseDouble(rendaFixa.getAjusteMTM()) : null);	
			bean.setIrProv(rendaFixa.getIrProv());
			rendaFixaXlsList.add(bean);
		}
		parameter.put("listaRendaFixaXLS", rendaFixaXlsList);
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "RF" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.RENDA_FIXA_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}
	
	public Map<String, byte[]> convertCdpPenaltyXlsx(Map <String, Object> parameter, Date dataConsulta, TbdwDmPessoa pessoa) {
		if(parameter.get("listaCdbPenalty") == null)
			return null;
		List<CdbPenaltyDTO> cdbPenaltyList = ((List<?>) parameter.get("listaCdbPenalty"))
				.stream()
				.map(row -> CdbPenaltyDTO.class.cast(row))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(cdbPenaltyList))
			return null;
		List<CdbPenaltyXlsDTO> cdbPenaltyXlsList = new ArrayList<>();
		for(CdbPenaltyDTO cdbPenalty : cdbPenaltyList) {
			CdbPenaltyXlsDTO bean = new CdbPenaltyXlsDTO();
			bean.setNmVeiculoLegal(cdbPenalty.getNmVeiculoLegal());
			bean.setNmContraparte(cdbPenalty.getNmContraparte());
			bean.setDocumentoContraparte(cdbPenalty.getDocumentoContraparte());
			bean.setNmProduto(cdbPenalty.getNmProduto());
			bean.setNmIndexador(cdbPenalty.getNmIndexador());
			bean.setData(cdbPenalty.getData());
			bean.setValorBruto(cdbPenalty.getValorBruto());
			bean.setValorProvisaoIOF(cdbPenalty.getValorProvisaoIOF());
			bean.setValorProvisaoIR(cdbPenalty.getValorProvisaoIR());
			bean.setValorLiquido(cdbPenalty.getValorLiquido());
			bean.setValorRendimento(cdbPenalty.getValorRendimento());
			bean.setValorAplicacao(cdbPenalty.getValorAplicacao());
			bean.setValorResgateBruto(cdbPenalty.getValorResgateBruto());
			bean.setValorIOFResgate(cdbPenalty.getValorIOFResgate());
			bean.setValorIRResgate(cdbPenalty.getValorIRResgate());
			bean.setValorResgateLiquido(cdbPenalty.getValorResgateLiquido());
			cdbPenaltyXlsList.add(bean);
		}
		parameter.put("listaCdbPenaltyXLS", cdbPenaltyXlsList);
		parameter.put("cdbPenaltyDataInicial", cdbPenaltyList.get(0).getData());
		parameter.put("cdbPenaltyIndexador", cdbPenaltyList.get(0).getNmIndexador());
		parameter.put("cdbPenaltyMercadoria", cdbPenaltyList.get(0).getNmProduto());
		Map <Object, Object > beans = new HashMap <Object, Object>();
		beans.put("master", parameter);
		String nomeArquivoXlsx = "CDBPENALTY" + new SimpleDateFormat("yyyyMMdd").format(dataConsulta) + "-" + StringUtils.trimToEmpty(pessoa.getNmPessoaAbrev()) + "-" + pessoa.getCdPessoa() + ".xlsx";
		try {
			return createXLSReport(DerivativeReport.CDB_PENALTY_XLS.getPath(), nomeArquivoXlsx, beans);
		} catch (Exception e) {
			throw new DerivativesException(StringUtils.defaultIfEmpty(e.getMessage(), "Erro ao gerar arquivo xlsx."));
		}
	}

	private Map<String, Object> addReportHeader(TbdwDmPessoa pessoa) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("URL_LOGO", this.getClass().getResourceAsStream("/images/deutsche_bank_logo.png"));
		parameters.put("URL_LOGO_AUX", this.getClass().getResourceAsStream("/images/deutsche_bank_square.png"));
		parameters.put("REPORT_DATE", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		parameters.put("NM_CLI", pessoa.getNmPessoa());
		parameters.put("CD_CLI_BR", pessoa.getCdPessoa().intValue());
		parameters.put("CNPJ", pessoa.getCdGcgCpf() != null ? format("##.###.###/####-##", StringUtils.leftPad(String.valueOf(pessoa.getCdGcgCpf()), 14, "0")) : "");
		parameters.put("ENDERECO", StringUtils.defaultIfBlank(StringUtils.trim(pessoa.getDsLogradouro()), "").concat(StringUtils.isNotBlank(StringUtils.trim(pessoa.getDsLogradouro())) ? ", " : "").concat(StringUtils.defaultIfBlank(StringUtils.trim(pessoa.getNrLogradouro()), "")).concat(StringUtils.isNotBlank(StringUtils.trim(pessoa.getNrLogradouro())) && StringUtils.isNotBlank(StringUtils.trim(pessoa.getDsComplLogr())) ? " - " : "").concat(StringUtils.defaultIfBlank(StringUtils.trim(pessoa.getDsComplLogr()), "")));
		parameters.put("ESTADO", StringUtils.defaultIfBlank(StringUtils.trim(pessoa.getDsBairro()), "").concat(StringUtils.isNotBlank(StringUtils.trim(pessoa.getDsBairro())) ? " - " : "").concat(StringUtils.defaultIfBlank(StringUtils.trim(pessoa.getDsCidade()), "")).concat(StringUtils.isNotBlank(StringUtils.trim(pessoa.getDsCidade())) && StringUtils.isNotBlank(StringUtils.trim(pessoa.getCdUf())) ? " - " : "").concat(StringUtils.defaultIfBlank(StringUtils.trim(pessoa.getCdUf()), "")));
		parameters.put("CEP", pessoa.getCdCep() != null ? format("#####-###", StringUtils.leftPad(String.valueOf(pessoa.getCdCep()), 8, "0")) : "");
		return parameters;
	}
	
	private Map<String, Object> getReportData(String usuario, Date dataConsulta, TbdwDmPessoa pessoa) {
		Map<String, Object> parameters = new HashMap<>();

		//SWAP
		parameters.put("swapReport", this.getClass().getResourceAsStream(DerivativeReport.SWAP.getPath()));
		List<SwapDTO> swap = derivativesRepository.swap(dataConsulta, pessoa.getCdPessoa());
		parameters.put("listaSwap", swap);
		
		//SWAP FLUXO
		parameters.put("swapFluxoReport", this.getClass().getResourceAsStream(DerivativeReport.SWAP_FLUXO.getPath()));
		List<PosicaoSwapDTO> posicoesSwap = derivativesRepository.findPosicaoSwap(dataConsulta, pessoa.getCdPessoa());
		List<SwapFluxoDTO> swapFluxo = new ArrayList<>();
		List<Integer> listIdOper = new ArrayList<Integer>();
		for(PosicaoSwapDTO posicaoSwap : posicoesSwap) {
			if(!listIdOper.contains(posicaoSwap.getIdOperacao())) {
				listIdOper.add(posicaoSwap.getIdOperacao());
				List<SwapFluxoDTO> swapFluxoParc = derivativesRepository.swapFluxo(dataConsulta, posicaoSwap.getIdOperacao());
				for(SwapFluxoDTO bean : swapFluxoParc) {
					bean.setNrContrato(posicaoSwap.getNrContrato());
					bean.setVlrBase(posicaoSwap.getVlrBase() != null ? posicaoSwap.getVlrBase().toString() : null);
					bean.setDtInicio(posicaoSwap.getDtInicio() != null ? DateFormatUtils.format(posicaoSwap.getDtInicio(), "dd/MM/yyyy") : null);
					bean.setDtVcto(posicaoSwap.getDtVecto() != null ? DateFormatUtils.format(posicaoSwap.getDtVecto(), "dd/MM/yyyy") : null);
					bean.setIndexParte(posicaoSwap.getIndexParte());
					bean.setIndexPartePerc(String.valueOf(posicaoSwap.getPercIndParte()));
					bean.setTaxaParte360(String.valueOf(posicaoSwap.getTx360Parte()));
					bean.setValorParte(String.valueOf(posicaoSwap.getFiscalParte()));
					bean.setIndexContraParte(posicaoSwap.getIndexCp());
					bean.setIndexContraPartePerc(String.valueOf(posicaoSwap.getPercINDCP()));
					bean.setTaxaContraParte360(String.valueOf(posicaoSwap.getTx360CP()));
					bean.setValorContraParte(String.valueOf(posicaoSwap.getFiscalCP()));
					bean.setAjusteFiscal(String.valueOf(posicaoSwap.getAjusteFiscal()));
					bean.setAjusteMTM(String.valueOf(posicaoSwap.getAjusteMtm()));
					bean.setIdOperacao(posicaoSwap.getIdOperacao());
				}
				swapFluxo.addAll(swapFluxoParc);
			}
		}
		parameters.put("listaSwapFluxo", swapFluxo);

		//NDF
		parameters.put("ndfReport", this.getClass().getResourceAsStream(DerivativeReport.NDF.getPath()));
		List<NdfDTO> ndf = derivativesRepository.ndf(dataConsulta, pessoa.getCdPessoa());
		parameters.put("listaNDF", ndf);
		
		//OPCOES
		parameters.put("opcoesReport", this.getClass().getResourceAsStream(DerivativeReport.OPCOES.getPath()));
		List<OpcaoFlexivelDTO> opcoes = derivativesRepository.opcoes(dataConsulta, pessoa.getNmPessoaAbrev());
		parameters.put("listaOpcoes", opcoes);
		
		//TERMOS
		parameters.put("termosReport", this.getClass().getResourceAsStream(DerivativeReport.TERMOS.getPath()));
		List<TermosDTO> termos = derivativesRepository.termos(dataConsulta, pessoa.getCdPessoa());
		parameters.put("listaTermos", termos);
		
		//RENDA FIXA
		parameters.put("rendaFixaReport", this.getClass().getResourceAsStream(DerivativeReport.RENDA_FIXA.getPath()));
		String pessoaRf = derivativesRepository.consultaPessoaRf(pessoa.getCdPessoa());
		List<RendaFixaDTO> rfConsulta = new ArrayList<>();
		if(StringUtils.isNotBlank(pessoaRf)) {
			if(StringUtils.isBlank(usuario))
				usuario = "APP-Extrato";
			derivativesRepository.rfGeracao(0, usuario.concat(pessoa.getCdPessoa().toString() ), dataConsulta, "", "", "", "", "", 0, pessoaRf, "N", "N");
			rfConsulta = derivativesRepository.rfConsulta(usuario.concat(pessoa.getCdPessoa().toString()));
			parameters.put("listaRendaFixa", rfConsulta);
		}
		
		// CDB com Penalty
		parameters.put("cdbPenaltyReport", this.getClass().getResourceAsStream(DerivativeReport.CDB_PENALTY.getPath()));
		List<CdbPenaltyDTO> cdbPenalty = derivativesRepository.cdbPenalty(dataConsulta, pessoa.getNmPessoaAbrev());
		parameters.put("listaCdbPenalty", cdbPenalty);
		
		if(CollectionUtils.isEmpty(swap) && CollectionUtils.isEmpty(swapFluxo) && CollectionUtils.isEmpty(ndf) && CollectionUtils.isEmpty(opcoes) && CollectionUtils.isEmpty(termos) && CollectionUtils.isEmpty(rfConsulta) && CollectionUtils.isEmpty(cdbPenalty))
			throw new EmptyResultDataAccessException("Não existem dados para essa data e cliente.", 1);
		return parameters;
	}
	
	private Map<String, byte[]> createXLSReport(String reportTemplatePath, String filename, Map<Object, Object> beans) throws FileNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
		try (InputStream is = this.getClass().getResourceAsStream(reportTemplatePath); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			XLSTransformer transformer = new XLSTransformer();
			Workbook wb = transformer.transformXLS(is, beans);
			wb.write(baos);
			Map<String, byte[]> reportMap = new HashMap<>();
			reportMap.put(filename, baos.toByteArray());
			return reportMap;
		}
	}
	
	private void logReport(boolean isAgendamento, Long cdCliente, String nomeArquivo, TbdwDmPessoa pessoa, ReportStatus reportStatus, String agTipoLog, String descricaoLog) {
		ExtractLogReportDTO extractLogReport = new ExtractLogReportDTO();
		extractLogReport.setCdCli(cdCliente);
		extractLogReport.setNmCli(pessoa != null ? pessoa.getNmPessoa() : null);
		extractLogReport.setAgTipo(StringUtils.defaultIfBlank(agTipoLog, ""));
		extractLogReport.setExecucao(isAgendamento ? ReportExecution.AGENDADO : ReportExecution.MANUAL);
		extractLogReport.setStatus(reportStatus);
		extractLogReport.setDescricao(descricaoLog);
		extractLogReport.setFilename(nomeArquivo);
		extractLogReport.setDtLog(new Date());
		extractLogReportService.insert(extractLogReport);
	}
	
	private boolean checkUserisRunningReport(String usuario, Long cdCliente) {
		if(StringUtils.isBlank(usuario) || cdCliente == null)
			return false;
		Date checkUserReport = reportTimeLimitRepositoryImpl.checkUserReport(usuario, cdCliente);
		if(checkUserReport == null)
			return false;
		if((new Date().getTime() - checkUserReport.getTime()) >= reportUtil.getTimelimit()) {
			reportTimeLimitRepositoryImpl.deleteReportTimeLimit(usuario, cdCliente);
			return false;
		}
		return true;
	}
	
	private String format(String pattern, Object value) {
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Boolean getLastDayOfMonth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		String dateLastOfMonth = dateFormat.format(cal.getTime());
		Date date = new Date();
		String dateCurrent = dateFormat.format(date);
		if (dateLastOfMonth.equals(dateCurrent)) {
			//return true if the current date is the last day of the month
			return true;
		} else {
			//return false if the current date is not the last day of the month
			return false;
		}
	}
	
}
