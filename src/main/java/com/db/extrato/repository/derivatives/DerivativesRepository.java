package com.db.extrato.repository.derivatives;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.db.extrato.dto.CdbPenaltyDTO;
import com.db.extrato.dto.NdfDTO;
import com.db.extrato.dto.OpcaoFlexivelDTO;
import com.db.extrato.dto.PosicaoSwapDTO;
import com.db.extrato.dto.RendaFixaDTO;
import com.db.extrato.dto.SwapDTO;
import com.db.extrato.dto.SwapFluxoDTO;
import com.db.extrato.dto.TermosDTO;

@Repository
public class DerivativesRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("derivDataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<SwapDTO> swap(Date data, Long codigoPessoa) {
		SqlParameterSource in = new MapSqlParameterSource()
		        .addValue("@param_date", new SimpleDateFormat("yyyy-MM-dd").format(data))
		        .addValue("@param_code", codigoPessoa);
		Map<String, Object> resultSet = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_REPORT_SBS_SWAP_EXTRATO")
				.useInParameterNames("@param_date", "@param_code")
				.declareParameters(new SqlParameter("@param_date", Types.VARCHAR), new SqlParameter("@param_code", Types.INTEGER))
				.returningResultSet("listSwap", new RowMapper<SwapDTO>() {
					public SwapDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						SwapDTO bean = new SwapDTO();
						bean.setNrContrato(rs.getString("NoContrato"));
						bean.setVlrBase(rs.getString("VlrBase"));
						bean.setDtInicio(rs.getDate("DtInicio"));
						bean.setDtVcto(rs.getDate("DtVcto"));
						bean.setIndexParte(rs.getString("IndexCP"));
						bean.setIndexPartePerc(rs.getString("PercIndexCP"));
						bean.setTaxaParte252(rs.getString("TX252CP"));
						bean.setTaxaParte360(rs.getString("TX360CP"));
						bean.setValorParte(rs.getString("FiscalCP"));
						bean.setIndexContraParte(rs.getString("IndexParte"));
						bean.setIndexContraPartePerc(rs.getString("PercIndexParte"));
						bean.setTaxaContraParte252(rs.getString("TX252Parte"));
						bean.setTaxaContraParte360(rs.getString("TX360Parte"));
						bean.setValorContraParte(rs.getString("FiscalParte"));
						bean.setAjusteFiscal(rs.getString("AjusteFiscal"));
						bean.setAjusteMTM(rs.getString("AjusteMTM"));
						return bean;
					}
				}).execute(in);
	    return ((List<?>) resultSet.get("listSwap"))
	    		.stream()
	    		.map(row -> SwapDTO.class.cast(row))
	    		.collect(Collectors.toList());
	}
	
	public List<SwapFluxoDTO> swapFluxo(Date data, Integer idOperacao) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("@param_date", new SimpleDateFormat("yyyy-MM-dd").format(data))
				.addValue("@param_id", idOperacao);
		Map<String, Object> resultSet = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_REPORT_SBS_SWAP_FLUXO_PARC")
				.useInParameterNames("@param_date", "@param_id")
				.declareParameters(new SqlParameter("@param_date", Types.VARCHAR), new SqlParameter("@param_id", Types.INTEGER))
				.returningResultSet("listSwapFluxo", new RowMapper<SwapFluxoDTO>() {
					public SwapFluxoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						SwapFluxoDTO bean = new SwapFluxoDTO();
						bean.setNrParcela(rs.getInt("No Parcela"));
						try {
							String dtInicioParcelaStr = rs.getString("Dt Inicio");
							Date dtInicioParcela = dtInicioParcelaStr != null ? new SimpleDateFormat("MM/dd/yy").parse(dtInicioParcelaStr) : null;
							bean.setDtInicioParcela(dtInicioParcela != null ? new SimpleDateFormat("dd/MM/yy").format(dtInicioParcela) : null);
						} catch (ParseException e) { }
						try {
							String dtVctoParcelaStr = rs.getString("Dt Vencto");
							Date dtVctoParcela = dtVctoParcelaStr != null ? new SimpleDateFormat("MM/dd/yy").parse(dtVctoParcelaStr) : null;
							bean.setDtVctoParcela(dtVctoParcela != null ? new SimpleDateFormat("dd/MM/yy").format(dtVctoParcela) : null);
						} catch (ParseException e) { }
						bean.setTaxaParcela252(rs.getString("Taxa 252"));
						bean.setTaxaParcela360(rs.getString("Taxa 360"));
						return bean;
					}
				})
				.execute(in);
		return ((List<?>) resultSet.get("listSwapFluxo"))
	    		.stream()
	    		.map(row -> SwapFluxoDTO.class.cast(row))
	    		.collect(Collectors.toList());
	}

	public List<PosicaoSwapDTO> findPosicaoSwap(Date data, Long codigoPessoa) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("	oSwap.contratoclearing			'NrContrato', ");
		query.append("	A.VALORBASEATUAL				'VlrBase', ");
		query.append("	A.DATAINICIO					'DtInicio', ");
		query.append("	A.DATAVENCTO 					'DtVecto', ");
		query.append("	(CASE ");
		query.append("	WHEN A.NMINDEXADORVL = 'PTAX-V' THEN ");
		query.append("	'USD' ");
		query.append("	ELSE A.NMINDEXADORVL ");
		query.append("	END) 							'IndexParte', ");
		query.append("	A.VLPERCINDEXADORVL				'PercINDParte', ");
		query.append("	A.TAXAVL360						'TX360Parte', ");
		query.append("	A.VALORVL						'FiscalParte', ");
		query.append("	(CASE ");
		query.append("	WHEN A.NMINDEXCONTRAPARTE = 'PTAX-V' THEN ");
		query.append("	'USD' ");
		query.append("	ELSE A.NMINDEXCONTRAPARTE ");
		query.append("	END) 							'IndexCP', ");
		query.append("	A.VLPERCINDEXADORCONTRAPARTE	'PercINDCP', ");
		query.append("	A.TAXACONTRAPARTE360			'TX360CP', ");
		query.append("	A.VALORCONTRAPARTE				'FiscalCP', ");
		query.append("	A.VALORCONTRAPARTE - A.VALORVL	'AjusteFiscal', ");
		query.append("	A.VALORCONTRAMTM - A.VALORVLMTM 'AjusteMTM', ");
		query.append("	idOperacao ");
		query.append("FROM V_RPT_POSICAOSWAP A ");
		query.append("INNER JOIN PESSOA B ON (A.CONTRAPARTE = B.NOMEREDUZIDO) ");
		query.append("INNER JOIN CLASSE C ON (A.CLASSE = C.DESCRICAO) ");
		query.append("INNER JOIN v_Operacaoswapaux oSwap on (idOperacao = oSwap.OperacaoSwap_ID) ");
		if (data != null) {
			query.append(" AND dataAtual = '" + new SimpleDateFormat("yyyy-MM-dd").format(data) + "' ");
		}
		if (codigoPessoa != null) {
			query.append(" AND CODIGO = " + codigoPessoa);
		}
		return jdbcTemplate.query(query.toString(), new RowMapper<PosicaoSwapDTO>() {
			public PosicaoSwapDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PosicaoSwapDTO dto = new PosicaoSwapDTO();
				  dto.setNrContrato(rs.getString("NrContrato"));
			      dto.setVlrBase(rs.getBigDecimal("VlrBase"));
			      dto.setDtInicio(rs.getDate("DtInicio"));
			      dto.setDtVecto(rs.getDate("DtVecto"));
			      dto.setIndexParte(rs.getString("IndexParte"));
			      dto.setPercIndParte(rs.getDouble("PercINDParte"));
			      dto.setTx360Parte(rs.getDouble("TX360Parte"));
			      dto.setFiscalParte(rs.getDouble("FiscalParte"));
			      dto.setIndexCp(rs.getString("IndexCP"));
			      dto.setPercINDCP(rs.getDouble("PercINDCP"));
			      dto.setTx360CP(rs.getDouble("TX360CP"));
			      dto.setFiscalCP(rs.getDouble("FiscalCP"));
			      dto.setAjusteFiscal(rs.getDouble("AjusteFiscal"));
			      dto.setAjusteMtm(rs.getDouble("AjusteMTM"));
			      dto.setIdOperacao(rs.getInt("idOperacao"));
			      return dto;
			}
		});
	}
	
	public List<NdfDTO> ndf(Date data, Long codigoPessoa) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("@param_date", new SimpleDateFormat("yyyy-MM-dd").format(data))
				.addValue("@param_code", codigoPessoa);
		Map<String, Object> resultSet = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_REPORT_SBS_NDF_EXTRATO")
				.useInParameterNames("@param_date", "@param_code")
				.declareParameters(new SqlParameter("@param_date", Types.VARCHAR), new SqlParameter("@param_code", Types.INTEGER))
				.returningResultSet("listNDF", new RowMapper<NdfDTO>() {
					public NdfDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						NdfDTO bean = new NdfDTO();
						bean.setNrContrato(rs.getString("No Contrato 2"));
						bean.setVlrNotional(rs.getString("Vlr Base 2"));
						bean.setVlrBase(rs.getString("Vlr Base (ME) 2"));
						bean.setDtInicio(rs.getDate("Dt Inicio 2"));
						bean.setDtVcto(rs.getDate("Dt Vcto 2"));
						bean.setMoedaRef(rs.getString("ME Ref 2"));
						bean.setCotTermo(rs.getString("TX Termo 2"));
						bean.setTaxPre252(rs.getString("Taxa Pre 2"));
						bean.setPosicao(rs.getString("C/V 2"));
						bean.setFiscalParte(rs.getString("Fiscal Parte 2"));
						bean.setFiscalContraParte(rs.getString("Fiscal CP 2"));
						bean.setAjusteFiscal(rs.getString("Ajuste Fiscal 2"));
						bean.setAjusteMTM(rs.getString("Ajuste MTM 2"));
						return bean;
					}
				})
				.execute(in);
		return ((List<?>) resultSet.get("listNDF"))
	    		.stream()
	    		.map(row -> NdfDTO.class.cast(row))
	    		.collect(Collectors.toList());
	}
	
	public List<OpcaoFlexivelDTO> opcoes(Date data, String nomeReduzido) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append("  (CASE WHEN  produto = 'OFCC' THEN 'Call' ");
		query.append(" 	ELSE CASE WHEN produto = 'OFVC' THEN 'Put' END ");
		query.append("   END) 'tipoOpcao', ");
		query.append("   (CASE WHEN  titularLancador = 'Lancador' THEN 'Titular' ");
		query.append(" 	ELSE CASE WHEN titularLancador = 'Titular' THEN 'Lançador' END ");
		query.append("   END) 'posicao', ");
		query.append("   (CASE WHEN objeto = 'PTAX-V' THEN 'USD' ");
		query.append(" 	ELSE objeto ");
		query.append("   END) 'moeda', ");
		query.append("   qtdeOriginal 'vlrBaseOrig', ");
		query.append("   qtdeAtual 'vlrBaseAtual', ");
		query.append("   (CASE WHEN  titularLancador = 'Titular' THEN (ABS(valorFinanceiro)) ");
		query.append(" 	ELSE CASE WHEN titularLancador = 'Lancador' THEN (ABS(valorFinanceiro) * -1) END ");
		query.append("   END) 'premio', ");
		query.append("   dataPremio 'dtPremio', ");
		query.append("   puExercicio 'precoExercicio', ");
		query.append("   dataInicio 'dtReg', ");
		query.append("   dataVencto 'dtVcto', ");
		query.append("   (CASE WHEN  tipoExercicio = 'Europeia' THEN 'Européia' ");
		query.append(" 	ELSE tipoExercicio ");
		query.append("   END) 'tipoExercicio', ");
		query.append("   dataCotVencto 'dtFixing', ");
		query.append("   fonteInformacao 'fonteInfo', ");
		query.append("   'CETIP' 'localReg', ");
		query.append("   instrFinanceiro 'codCetip', ");
		query.append("   (CASE WHEN titularLancador = 'Lancador' THEN vlMTM ELSE (vlMTM * -1) END) 'valorMTM' ");
		query.append(" FROM v_RPT_PosicaoOpcaoFlexivel ");
		query.append(" WHERE produto IN ('OFCC', 'OFVC') and titularLancador IN ('Lancador', 'Titular') ");
		query.append(" and dataAtual = '" + new SimpleDateFormat("yyyy-MM-dd").format(data) + "' and contraparte = '" + nomeReduzido + "' ");
		query.append(" ORDER BY produto, titularLancador, dataInicio, dataVencto ");
		return jdbcTemplate.query(query.toString(), new RowMapper<OpcaoFlexivelDTO>() {
			public OpcaoFlexivelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OpcaoFlexivelDTO bean = new OpcaoFlexivelDTO();
				bean.setTipoOpcao(rs.getString("tipoOpcao"));
				bean.setPosicao(rs.getString("posicao"));
				bean.setMoeda(rs.getString("moeda"));
				bean.setVlrBaseOrig(rs.getDouble("vlrBaseOrig"));
				bean.setVlrBaseAtual(rs.getDouble("vlrBaseAtual"));
				bean.setPremio(rs.getDouble("premio"));
				bean.setDtPremio(rs.getDate("dtPremio"));
				bean.setPrecoExercicio(rs.getDouble("precoExercicio"));
				bean.setDtReg(rs.getDate("dtReg"));
				bean.setDtVcto(rs.getDate("dtVcto"));
				bean.setTipoExercicio(rs.getString("tipoExercicio"));
				bean.setDtFixing(rs.getDate("dtFixing"));
				bean.setFonteInfo(rs.getString("fonteInfo"));
				bean.setLocalReg(rs.getString("localReg"));
				bean.setCodCetip(rs.getString("codCetip"));
				bean.setValorMTM(rs.getDouble("valorMTM"));
				return bean;
			}
		});
	}
	
	public List<TermosDTO> termos(Date data, Long codigoPessoa) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT (CASE WHEN  posicao = 'Comprador' THEN 'Vendedor' ");
		query.append(" ELSE CASE WHEN posicao = 'Vendedor' THEN 'Comprador' ");
		query.append(" END ");
		query.append(" END) AS posicao, ");
		query.append(" dataInicio, ");
		query.append(" dataVencto, ");
		query.append(" mercadoria, ");
		query.append(" unidadeNegociacao, ");
		query.append(" bolsaReferencia, ");
		query.append(" quantidade, ");
		query.append(" precoOperacao, ");
		query.append(" (CASE ");
		query.append(" WHEN moeda = 'PTAX-V' THEN ");
		query.append(" 'USD' ");
		query.append(" ELSE moeda ");
		query.append(" END)	 'moeda', ");
		query.append(" (CASE WHEN (cotacaoSpot = 0 or cotacaoSpot is null) THEN cotacaoInicial ");
		query.append(" ELSE cotacaoSpot ");
		query.append(" END) 'cotacaoSpot', ");
		query.append(" contrato, ");
		query.append(" (CASE WHEN  posicao = 'Comprador' THEN valorAjusteVendedor ");
		query.append(" ELSE CASE WHEN posicao = 'Vendedor' THEN valorAjusteComprador END ");
		query.append(" END) 'valorMTM' ");
		query.append(" FROM v_RPT_PosicTermoMercadoria ");
		query.append(" WHERE tipoCurva='MARK TO MARKET' AND posicao IN ('Comprador', 'Vendedor') ");
		query.append(" and dataAtual = '" + new SimpleDateFormat("yyyy-MM-dd").format(data) + "' and codGlobal = " + codigoPessoa + " ");
		query.append(" ORDER BY mercadoria, posicao, dataInicio, dataVencto ");
		return jdbcTemplate.query(query.toString(), new RowMapper<TermosDTO>() {
			public TermosDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				TermosDTO bean = new TermosDTO();
				bean.setPosicao(rs.getString("posicao"));
				bean.setDataInicio(rs.getDate("dataInicio"));
				bean.setDataVencto(rs.getDate("dataVencto"));
				bean.setMercadoria(rs.getString("mercadoria"));
				bean.setUnidadeNegociacao(rs.getString("unidadeNegociacao"));
				bean.setBolsaReferencia(rs.getString("bolsaReferencia"));
				bean.setQuantidade(rs.getInt("quantidade"));
				bean.setPrecoOperacao(rs.getDouble("precoOperacao"));
				bean.setMoeda(rs.getString("moeda"));
				bean.setCotacaoSpot(rs.getDouble("cotacaoSpot"));
				bean.setContrato(rs.getString("contrato"));
				bean.setValorMTM(rs.getDouble("valorMTM"));
				return bean;
			}
		});
	}
	
	public String consultaPessoaRf(Long codigoPessoa) {
		SqlParameterSource in = new MapSqlParameterSource().addValue("@param_code", codigoPessoa);
		Map<String, Object> resultSet = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_INT_ExtratoRF_ConsultaPessoa")
				.useInParameterNames("@param_code")
				.declareParameters(new SqlParameter("@param_code", Types.CHAR))
				.returningResultSet("listRFConsultaPessoa", new SingleColumnRowMapper<String>()).execute(in);
		return ((List<?>) resultSet.get("listRFConsultaPessoa"))
				.stream()
				.map(row -> String.class.cast(row))
				.findFirst()
				.orElse(null);
	}
	
	public void rfGeracao(int int_vbsql, String str_controle, Date dat_dtbase, String str_posicao,
			String str_custodiante, String str_atv_ap, String str_depto, String str_atvfg, int int_trazopvencendo,
			String str_contraparte, String str_tipoctparte, String str_totporctaparte) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("@int_vbsql", int_vbsql)
				.addValue("@str_controle", str_controle)
				.addValue("@dat_dtbase", new SimpleDateFormat("yyyy-MM-dd").format(dat_dtbase))
				.addValue("@str_posicao", str_posicao)
				.addValue("@str_custodiante", str_custodiante)
				.addValue("@str_atv_ap", str_atv_ap)
				.addValue("@str_depto", str_depto)
				.addValue("@str_atvfg", str_atvfg)
				.addValue("@int_trazopvencendo", int_trazopvencendo)
				.addValue("@str_contraparte", str_contraparte)
				.addValue("@str_tipoctparte", str_tipoctparte)
				.addValue("@str_totporctaparte", str_totporctaparte);
		new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_INT_ExtratoRF_Geracao")
				.useInParameterNames(
						"@int_vbsql", 
						"@str_controle", 
						"@dat_dtbase", 
						"@str_posicao", 
						"@str_custodiante",
						"@str_atv_ap", 
						"@str_depto", 
						"@str_atvfg", 
						"@int_trazopvencendo", 
						"@str_contraparte",
						"@str_tipoctparte", 
						"@str_totporctaparte")
				.declareParameters(
						new SqlParameter("@int_vbsql", Types.INTEGER),
						new SqlParameter("@str_controle", Types.VARCHAR),
						new SqlParameter("@dat_dtbase", Types.VARCHAR), 
						new SqlParameter("@str_posicao", Types.VARCHAR),
						new SqlParameter("@str_custodiante", Types.VARCHAR),
						new SqlParameter("@str_atv_ap", Types.VARCHAR), 
						new SqlParameter("@str_depto", Types.VARCHAR),
						new SqlParameter("@str_atvfg", Types.VARCHAR),
						new SqlParameter("@int_trazopvencendo", Types.INTEGER),
						new SqlParameter("@str_contraparte", Types.VARCHAR),
						new SqlParameter("@str_tipoctparte", Types.VARCHAR),
						new SqlParameter("@str_totporctaparte", Types.VARCHAR))
				.execute(in);
	}
	
	public List<RendaFixaDTO> rfConsulta(String param_code) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("@param_code", param_code);
		Map<String, Object> resultSet = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("SP_INT_ExtratoRF_Consulta")
				.useInParameterNames("@param_code")
				.declareParameters(
						new SqlParameter("@param_code", Types.VARCHAR))
				.returningResultSet("listRFConsulta", new RowMapper<RendaFixaDTO>() {
					public RendaFixaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						String indexador = (rs.getString("Indexador"));
						// If PTAX-V then USD
						if (indexador.equals("PTAX-V")) {
							indexador = "USD";
						}
						String irProv = rs.getString("IR PROV");
						RendaFixaDTO bean = new RendaFixaDTO();
						bean.setMercadoria(rs.getString("Mercadoria"));
						bean.setIndexador(indexador);
						bean.setDtEmissao(rs.getDate("Dt Emissao"));
						bean.setDtVcto(rs.getDate("Dt Vcto"));
						bean.setVlrBase(rs.getString("Vlr Base"));
						bean.setTaxaOpe(rs.getString("Taxa Ope"));
						bean.setTaxa252(rs.getString("Taxa 252"));
						bean.setTaxa360(rs.getString("Taxa 360"));
						bean.setQtde(rs.getString("Qtde"));
						bean.setPerc(rs.getString("Perc"));
						bean.setAjusteFiscal(rs.getString("Ajuste Fiscal"));
						bean.setAjusteMTM(rs.getString("Ajuste MTM"));
						bean.setIrProv(Math.abs(Double.parseDouble(irProv)) * -1);
						return bean;
					}
				}).execute(in);
		return ((List<?>) resultSet.get("listRFConsulta"))
	    		.stream()
	    		.map(row -> RendaFixaDTO.class.cast(row))
	    		.collect(Collectors.toList());
	}
	
	public List<CdbPenaltyDTO> cdbPenalty(Date data, String nomeReduzido) {
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("@dataReferencia", new SimpleDateFormat("yyyy-MM-dd").format(data));
		Map<String, Object> resultSetSaldo = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_ExtratoCDBPenalti_Saldo")
				.useInParameterNames("@dataReferencia")
				.declareParameters(
						new SqlParameter("@dataReferencia", Types.VARCHAR))
				.returningResultSet("listCDBPenaltySaldo", new RowMapper<CdbPenaltyDTO>() {
					public CdbPenaltyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						CdbPenaltyDTO bean = new CdbPenaltyDTO();
						bean.setNmVeiculoLegal(rs.getString("nmVeiculoLegal"));
						bean.setNmContraparte(rs.getString("nmContraparte"));
						bean.setDocumentoContraparte(rs.getString("documentoContraparte"));
						bean.setNmProduto(rs.getString("nmProduto"));
						bean.setNmIndexador(rs.getString("nmIndexador"));
						bean.setData(DateFormatUtils.format(rs.getDate("data"), "dd/MM/yyyy"));
						bean.setValorBruto(rs.getString("valorBruto"));
						bean.setValorProvisaoIOF(rs.getString("valorProvisaoIOF"));
						bean.setValorProvisaoIR(rs.getString("valorProvisaoIR"));
						bean.setValorLiquido(rs.getString("valorLiquido"));
						bean.setValorRendimento(rs.getString("valorRendimento"));
						return bean;
					}
				}).execute(in);
		Map<String, Object> resultSetMovimento = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_ExtratoCDBPenalti_Movimento")
				.useInParameterNames("@dataReferencia")
				.declareParameters(
						new SqlParameter("@dataReferencia", Types.VARCHAR))
				.returningResultSet("listCDBPenaltyMovimento", new RowMapper<CdbPenaltyDTO>() {
					public CdbPenaltyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						CdbPenaltyDTO bean = new CdbPenaltyDTO();
						bean.setNmVeiculoLegal(rs.getString("nmVeiculoLegal"));
						bean.setNmContraparte(rs.getString("nmContraparte"));
						bean.setDocumentoContraparte(rs.getString("documentoContraparte"));
						bean.setNmProduto(rs.getString("nmProduto"));
						bean.setNmIndexador(rs.getString("nmIndexador"));
						bean.setData(DateFormatUtils.format(rs.getDate("data"), "dd/MM/yyyy"));
						bean.setValorAplicacao(rs.getString("valorAplicacao"));
						bean.setValorResgateBruto(rs.getString("valorResgateBruto"));
						bean.setValorIOFResgate(rs.getString("valorIOFResgate"));
						bean.setValorIRResgate(rs.getString("valorIRResgate"));
						bean.setValorResgateLiquido(rs.getString("valorResgateLiquido"));
						return bean;
					}
				}).execute(in);
		List<CdbPenaltyDTO> listSaldo = new ArrayList<CdbPenaltyDTO>();
    	listSaldo.addAll(removeClients(((List<?>) resultSetSaldo.get("listCDBPenaltySaldo")).stream().map(row -> CdbPenaltyDTO.class.cast(row)).collect(Collectors.toList()), nomeReduzido));
    	List<CdbPenaltyDTO> listMovimento = new ArrayList<CdbPenaltyDTO>();
    	listMovimento.addAll(removeClients(((List<?>) resultSetMovimento.get("listCDBPenaltyMovimento")).stream().map(row -> CdbPenaltyDTO.class.cast(row)).collect(Collectors.toList()), nomeReduzido));
		for (CdbPenaltyDTO list : listMovimento) {
			for (int i = 0; i < listSaldo.size(); i++) {
				if ((listSaldo.get(i).getDocumentoContraparte().equalsIgnoreCase(list.getDocumentoContraparte()))
						&& (listSaldo.get(i).getNmProduto().equalsIgnoreCase(list.getNmProduto()))
						&& (listSaldo.get(i).getNmContraparte().equalsIgnoreCase(list.getNmContraparte()))
						&& (listSaldo.get(i).getNmVeiculoLegal().equalsIgnoreCase(list.getNmVeiculoLegal()))
						&& (listSaldo.get(i).getNmIndexador().equalsIgnoreCase(list.getNmIndexador()))
						&& (listSaldo.get(i).getData().equalsIgnoreCase(list.getData()))) {
					listSaldo.get(i).setValorAplicacao(list.getValorAplicacao());
					listSaldo.get(i).setValorResgateBruto(list.getValorResgateBruto());
					listSaldo.get(i).setValorIOFResgate(list.getValorIOFResgate());
					listSaldo.get(i).setValorIRResgate(list.getValorIRResgate());
					listSaldo.get(i).setValorResgateLiquido(list.getValorResgateLiquido());
					continue;
				}
			}
		}
    	return listSaldo;    
	}
	
	public List<CdbPenaltyDTO> removeClients(List<CdbPenaltyDTO> clientList, String nomeClient) {
		List<CdbPenaltyDTO> correctClients = new ArrayList<CdbPenaltyDTO>(clientList);
		for (int i = correctClients.size() - 1; i >= 0; i--) {
			if (!correctClients.get(i).getNmContraparte().equalsIgnoreCase(nomeClient)) {
				correctClients.remove(i);
			}
		}
		return correctClients;
	}

}
