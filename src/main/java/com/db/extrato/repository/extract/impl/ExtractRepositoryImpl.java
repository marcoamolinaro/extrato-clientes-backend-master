package com.db.extrato.repository.extract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.ExtractFilter;
import com.db.extrato.dto.TexExtratoDTO;
import com.db.extrato.enums.ReportName;
import com.db.extrato.enums.ReportType;

@Repository
public class ExtractRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<TexExtratoDTO> findExtractFilter(ExtractFilter extractFilter,  ReportName reportName, ReportType reportType, Pageable page) {

		StringBuilder query = new StringBuilder();

		query.append(" SELECT ");
		
		query.append(" E.CD_EMPRESA,  E.CD_CLIENTE,   E.NM_CLIENTE,   E.CD_CGCCPF,   E.DC_END1, E.DC_END2, E.DC_END3,");
		query.append(" E.CD_PAIS, E.NOME_AOSCUIDADOS,  E.DS_OBN,       E.NM_EMPRESA,   E.CD_FILIAL,   E.NM_INDEXADOR,  E.CD_GARANTIA,");
		query.append(" E.DT_INICIO_OPER, E.DT_VENCIMENTO_OPER, M.CD_GRPROD, M.CD_FAMPROD,");
		query.append(" E.CD_MOEDA,  E.II_TIPO_CONTABIL, E.CD_CGCCPF, E.CD_MODALIDADE, E.CD_OPER_ORIGEM,  E.CD_CONTRATO, E.DT_REFERENCIA, E.DT_INICIO_OPER, E.DT_VENCIMENTO_OPER,");
		query.append(" E.CD_GRPROD, E.SG_SISTEMA, E.SG_MODULO, VL_CONTABIL, VL_CORRIGIDO_ME, VL_JUROS_ME, VL_JUROS_MN, DT_LIQUIDACAO_OPER, VL_OPERACAO_EMP, ");

		if (ReportType.ANALYTIC.name().equals(reportType.name())) {
		     query.append(" E.QUANTIDADE, NVL( E.VL_CONTABIL, '0' ) VL_OPERACAO_MN,    NVL( E.VL_CORRIGIDO_ME, '0' ) VL_OPERACAO_ME,");
		     
		 } else if (ReportType.CONSOLIDATE.name().equals(reportType.name())){
		     query.append(" SUM(E.QUANTIDADE)QUANTIDADE, NVL( SUM( E.VL_CONTABIL ), '0' ) VL_OPERACAO_MN, NVL( SUM( E.VL_CORRIGIDO_ME ), '0' ) VL_OPERACAO_ME,");
		 }else {
           query.append(" E.QUANTIDADE, NVL( E.VL_OPERACAO_MN, '0' ) VL_OPERACAO_MN,    NVL( E.VL_OPERACAO_ME, '0' ) VL_OPERACAO_ME,");
		 }


	    query.append(" A.DS_NEGOCIO, A.DS_NEGOCIO_INGLES, A.DISPLAY_QTDE, A.DISPLAY_MN, A.DISPLAY_ME,");

	    query.append(" C.CD_MOEDA_ISO,");
	    query.append(" C.NM_MOEDA,");

	    query.append(" M.DS_MODALIDADE, M.CD_FAMPROD,  M.CD_PRODUTO, M.DS_PRODUTO");

	    query.append("  FROM EXTRATO_APP.TEX_EXTRATO E, EXTRATO_APP.TBL_MOD_APLIC A, EXTRATO_APP.TBDW_DIM_MOE C, EXTRATO_APP.TBDW_DIM_MODALIDADE M ");

	    query.append(" WHERE 1=1 ");

	    query.append("  AND (A.II_TIPO_CONTABIL = E.II_TIPO_CONTABIL OR A.II_TIPO_CONTABIL IS NULL)");
	    query.append("  AND (A.DS_OBN = E.DS_OBN OR A.DS_OBN IS NULL)");
	    query.append("  AND (A.NM_INDEXADOR = E.NM_INDEXADOR OR A.NM_INDEXADOR IS NULL)");
	    query.append("  AND (A.CD_GARANTIA = E.CD_GARANTIA OR A.CD_GARANTIA IS NULL)");
	    
	    query.append("  AND (A.CG_SISTEMA = E.SG_SISTEMA OR A.CG_SISTEMA IS NULL)");
	    query.append("  AND (A.CG_MODULO = E.SG_MODULO OR A.CG_MODULO IS NULL) ");
	    query.append("  AND (E.CD_MODALIDADE = A.CD_MODALIDADE)");
	    query.append("  AND (E.CD_MODALIDADE = M.CD_MODALIDADE)");
	    query.append("  AND (E.CD_MOEDA = C.CD_MOEDA)");
		
	    if (extractFilter.getCdEmpresa() == null
	        || (extractFilter.getCdEmpresa() != null && extractFilter.getCdEmpresa().equals(101101))) {
	      query.append("    AND ( E.CD_EMPRESA = 101101 OR E.CD_EMPRESA BETWEEN 300000 and 499999 ) ");
	    } else {
	      query.append("    AND ( E.CD_EMPRESA = " + extractFilter.getCdEmpresa() + ")");
	    }

	    if (ReportName.MONTHLY_REPORT.name().equals(reportName.name())) {
	      query.append(" AND TO_CHAR(E.DT_REFERENCIA,'MM-yyyy') = '" + extractFilter.getDtReferencia() + "'");
	      query.append(" AND (A.CD_APLIC = 1)");
	    } else if(ReportName.ANNUAL_REPORT.name().equals(reportName.name())) {
	      query.append(" AND TO_CHAR(E.DT_REFERENCIA,'yyyy') = '" + extractFilter.getDtReferencia().substring(3, 7) + "'");
	      query.append(" AND (A.CD_APLIC = 1)");
	    }

	    if (extractFilter.getCdCliente() != null && extractFilter.getCdCliente().trim().length() > 0) {
	      query.append(" AND E.CD_CLIENTE = " + extractFilter.getCdCliente());
	    }

	    if (extractFilter.getCdModalidade() != null && extractFilter.getCdModalidade().trim().length() > 0) {
	      query.append(" AND E.CD_MODALIDADE = " + extractFilter.getCdModalidade());
	    }
	    
	    if (extractFilter.getCdOperOrigem() != null && extractFilter.getCdOperOrigem().trim().length() > 0) {
	    	query.append(" AND E.CD_OPER_ORIGEM = '" + extractFilter.getCdOperOrigem() + "'");
	    }

	    if (ReportType.CONSOLIDATE.name().equals(reportType.name())) {
	      query.append(" GROUP BY ");
	      query.append(" E.CD_EMPRESA,  E.CD_CLIENTE,   E.NM_CLIENTE,   E.CD_CGCCPF,   E.DC_END1, E.DC_END2, E.DC_END3,");
	      query.append(" E.CD_PAIS, E.NOME_AOSCUIDADOS,  E.DS_OBN,       E.NM_EMPRESA,   E.CD_FILIAL,   E.NM_INDEXADOR,  E.CD_GARANTIA,");
	      query.append(" E.DT_INICIO_OPER, E.DT_VENCIMENTO_OPER, M.CD_GRPROD, M.CD_FAMPROD,");
	      query.append(" E.CD_MOEDA,  E.II_TIPO_CONTABIL, E.CD_CGCCPF, E.CD_MODALIDADE, E.CD_OPER_ORIGEM, E.CD_CONTRATO, E.DT_REFERENCIA, E.DT_INICIO_OPER, E.DT_VENCIMENTO_OPER,");
	      query.append(" E.CD_GRPROD, E.SG_SISTEMA, E.SG_MODULO,");
	      query.append(" A.DS_NEGOCIO, A.DS_NEGOCIO_INGLES, A.DISPLAY_QTDE, A.DISPLAY_MN, A.DISPLAY_ME,");
	      query.append(" C.CD_MOEDA_ISO,");
	      query.append(" C.NM_MOEDA, VL_CONTABIL, VL_CORRIGIDO_ME, VL_JUROS_ME, VL_JUROS_MN, DT_LIQUIDACAO_OPER, VL_OPERACAO_EMP,");
	      query.append(" M.DS_MODALIDADE, M.CD_FAMPROD, M.CD_PRODUTO, M.DS_PRODUTO");
	    }

	    if (page.getSort() != null) {

	      page.getSort().forEach(order -> {

	        if (order.getProperty().equals("nmCliente")) {
	          query.append(" ORDER BY E.NM_CLIENTE " + order.getDirection());
	        }
	      });

	    }

	    query.append("  OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY");

	    List<TexExtratoDTO> data = jdbcTemplate.query(query.toString(), new ExtractRowMapper());
	    return data;


	}

	public List<TexExtratoDTO> findExtractReport(ExtractFilter extractFilter, ReportName reportName, ReportType reportType, Pageable page) throws ParseException {
		StringBuilder query = new StringBuilder();

		
		if (ReportType.ANALYTIC.name().equals(reportType.name())) {

	      query.append(" SELECT ");

	      query.append(" A.CD_EMPRESA,  CD_CLIENTE, A.NM_CLIENTE, A.DC_END1, A.DC_END2, A.DC_END3,  A.CD_CGCCPF, DT_REFERENCIA,");
	      query.append(" P.CD_PRODUTO, DS_PRODUTO, C.CD_MOEDA_ISO, C.CD_MOEDA, NM_CLIENTE,");
	      query.append(" QUANTIDADE, NVL( VL_CONTABIL, '0' ) VL_OPERACAO_MN,    NVL( VL_CORRIGIDO_ME, '0' ) VL_OPERACAO_ME, DS_NEGOCIO_INGLES, DS_NEGOCIO, A.DT_INICIO_OPER, A.DT_VENCIMENTO_OPER, A.CD_MODALIDADE, SG_SISTEMA, SG_MODULO,");

	      query.append(" NVL( display_qtde, 'N' ) display_qtde,");
	      query.append(" NVL( display_me, 'N' ) display_me,");
	      query.append(" NVL( display_mn, 'N' ) display_mn");
	      
	      
	      query.append(" FROM TEX_EXTRATO A, EXTRATO_APP.TBDW_DIM_MOE C, EXTRATO_APP.TBDW_DIM_MODALIDADE p, EXTRATO_APP.TBL_MOD_APLIC M WHERE 1=1");

	      query.append(" AND (A.CD_MOEDA = C.CD_MOEDA)");
	      query.append(" AND (A.CD_MODALIDADE = p.CD_MODALIDADE)");
	      
	      query.append(" AND (M.CD_MODALIDADE = A.CD_MODALIDADE)");
	      query.append(" AND (M.II_TIPO_CONTABIL = A.II_TIPO_CONTABIL OR M.II_TIPO_CONTABIL IS NULL)");
	      query.append(" AND (M.DS_OBN = A.DS_OBN OR M.DS_OBN IS NULL)");
	      query.append(" AND (M.NM_INDEXADOR = A.NM_INDEXADOR OR M.NM_INDEXADOR IS NULL)");
	      query.append(" AND (M.CD_GARANTIA = A.CD_GARANTIA OR M.CD_GARANTIA IS NULL)");
	      query.append(" AND (M.CG_SISTEMA = A.SG_SISTEMA OR M.CG_SISTEMA IS NULL)");
	      query.append(" AND (M.CG_MODULO = A.SG_MODULO OR M.CG_MODULO IS NULL) ");
	      query.append(" AND (A.CD_MODALIDADE = M.CD_MODALIDADE)");
	      query.append(" AND (M.CD_APLIC = 1)");

	      query.append(" AND (VL_CONTABIL != 0 OR VL_CORRIGIDO_ME != 0)");

	    } else {

	      query.append(" SELECT ");

	      query.append(" A.CD_EMPRESA,   A.NM_CLIENTE, A.DC_END1, A.DC_END2, A.DC_END3,  A.CD_CGCCPF, DT_REFERENCIA, CD_CLIENTE,");
	      query.append(" P.CD_PRODUTO, C.CD_MOEDA,  DS_NEGOCIO_INGLES, DS_NEGOCIO, A.DT_INICIO_OPER, A.DT_VENCIMENTO_OPER,");
	      query.append(" A.CD_MODALIDADE, NM_CLIENTE, SG_SISTEMA, SG_MODULO, SUM(QUANTIDADE)QUANTIDADE,  SUM( VL_CONTABIL )VL_OPERACAO_MN, CD_MOEDA_ISO,");
	      query.append(" NVL( SUM( VL_CORRIGIDO_ME ), '0' ) VL_OPERACAO_ME, NVL( display_qtde, 'N' ) display_qtde, NVL( display_me, 'N' ) display_me, NVL( display_mn, 'N' ) display_mn");
	      
	      query.append(" FROM EXTRATO_APP.TEX_EXTRATO A, FDWB.TBDW_DIM_MOE C, EXTRATO_APP.TBDW_DIM_MODALIDADE p, EXTRATO_APP.TBL_MOD_APLIC M WHERE 1=1");

	      query.append(" AND (A.CD_MOEDA = C.CD_MOEDA)");
	      query.append(" AND (A.CD_MODALIDADE = p.CD_MODALIDADE)");

	      query.append(" AND (M.CD_MODALIDADE = A.CD_MODALIDADE)");
	      query.append(" AND (M.II_TIPO_CONTABIL = A.II_TIPO_CONTABIL OR M.II_TIPO_CONTABIL IS NULL)");
	      query.append(" AND (M.DS_OBN = A.DS_OBN OR M.DS_OBN IS NULL)");
	      query.append(" AND (M.NM_INDEXADOR = A.NM_INDEXADOR OR M.NM_INDEXADOR IS NULL)");
	      query.append(" AND (M.CD_GARANTIA = A.CD_GARANTIA OR M.CD_GARANTIA IS NULL)");
	      query.append(" AND (M.CG_SISTEMA = A.SG_SISTEMA OR M.CG_SISTEMA IS NULL)");
	      query.append(" AND (M.CG_MODULO = A.SG_MODULO OR M.CG_MODULO IS NULL) ");
	      query.append(" AND (A.CD_MODALIDADE = M.CD_MODALIDADE)");
	      query.append(" AND (M.CD_APLIC = 1)");
	      
	    }
		
		if (extractFilter.getCdEmpresa() != null) {
			query.append(" AND A.CD_EMPRESA = "+extractFilter.getCdEmpresa());
		}

		if (extractFilter.getCdCliente() != null && extractFilter.getCdCliente().trim().length() > 0) {
			query.append(" AND A.CD_CLIENTE = "+extractFilter.getCdCliente());
		}

		if (extractFilter.getCdModalidade() != null && extractFilter.getCdModalidade().trim().length() > 0) {
			query.append(" AND A.CD_MODALIDADE = "+extractFilter.getCdModalidade());
		}

		if(ReportName.MONTHLY_REPORT.name().equals(reportName.name())) {
			query.append(" AND TO_CHAR(DT_REFERENCIA,'MM-yyyy') = '"+extractFilter.getDtReferencia()+"'");
		}else {
			query.append(" AND TO_CHAR(DT_REFERENCIA,'yyyy') = '"+extractFilter.getDtReferencia().substring(3, 7)+"'");
		}
		
		query.append(" AND (VL_CONTABIL != 0 OR VL_CORRIGIDO_ME != 0) ");
		
		if(ReportType.CONSOLIDATE.name().equals(reportType.name())) {
			query.append(" GROUP BY A.CD_EMPRESA, DS_NEGOCIO, DS_NEGOCIO_INGLES, A.NM_CLIENTE, A.DC_END1, A.DC_END2, A.DC_END3, A.CD_CGCCPF, C.CD_MOEDA, SG_SISTEMA, SG_MODULO, CD_MOEDA_ISO, CD_CLIENTE,");
	          query.append("  P.CD_PRODUTO, A.CD_MODALIDADE, display_qtde, display_me, display_mn, A.CD_EMPRESA, A.DT_INICIO_OPER, A.DT_VENCIMENTO_OPER, A.CD_GARANTIA, DS_NEGOCIO, A.CD_PRODUTO, DT_REFERENCIA ");
	          query.append(" ORDER BY A.CD_EMPRESA ");
	          page.getSort().forEach(order -> {
	            
	            if (order.getProperty().equals("cdCliente")) {
	              query.append("  ,A.CD_CLIENTE " + order.getDirection());
	            }
	            
	            if(order.getProperty().equals("nmCliente")) {
	              query.append(" ,A.NM_CLIENTE "+ order.getDirection());
	            }
	          });
	          query.append("  ,DS_NEGOCIO, DS_NEGOCIO_INGLES, A.CD_PRODUTO, CD_MOEDA_ISO ");
		}else {
		  
		  query.append(" ORDER BY A.CD_EMPRESA ");
		  page.getSort().forEach(order -> {
		    
		    if (order.getProperty().equals("cdCliente")) {
		      query.append("  ,A.CD_CLIENTE " + order.getDirection());
		    }
		    
		    if(order.getProperty().equals("nmCliente")) {
		      query.append(" ,A.NM_CLIENTE "+ order.getDirection());
		    }
		  });
		  query.append(" ,A.SG_SISTEMA, DS_NEGOCIO, DS_NEGOCIO_INGLES, A.CD_PRODUTO,  C.CD_MOEDA_ISO, A.DC_END1, A.DC_END2, A.DC_END3, A.CD_CGCCPF, SG_MODULO, A.CD_MODALIDADE, ");
		  
		  query.append(" display_qtde,");
		  query.append(" display_me,");
		  query.append(" display_mn,");
		  query.append(" A.DT_INICIO_OPER, A.DT_VENCIMENTO_OPER, A.CD_GARANTIA ");
		  
		}
		  
		
		List<TexExtratoDTO> data = jdbcTemplate.query(query.toString(), new ExtractRowMapperExtraxt(reportType));
		return data;
	}
		

	public Long total(ExtractFilter extractFilter, ReportType reportType) {

	    StringBuilder query = new StringBuilder();

	    
	    if(ReportType.ANALYTIC.name().equalsIgnoreCase(reportType.name())) {
	      
	      query.append(" SELECT ");
	      
	      query.append(" CAST (COUNT(*)AS INTEGER) QUANTIDADE");
	      
	      query.append("  FROM EXTRATO_APP.TEX_EXTRATO E, EXTRATO_APP.TBL_MOD_APLIC A, EXTRATO_APP.TBDW_DIM_MOE C, EXTRATO_APP.TBDW_DIM_MODALIDADE M ");
	      
	      query.append(" WHERE 1=1 ");
	      
	      query.append("  AND (A.II_TIPO_CONTABIL = E.II_TIPO_CONTABIL OR A.II_TIPO_CONTABIL IS NULL)");
	      query.append("  AND (A.DS_OBN = E.DS_OBN OR A.DS_OBN IS NULL)");
	      query.append("  AND (A.NM_INDEXADOR = E.NM_INDEXADOR OR A.NM_INDEXADOR IS NULL)");
	      query.append("  AND (A.CD_GARANTIA = E.CD_GARANTIA OR A.CD_GARANTIA IS NULL)");
	      
	      query.append("  AND (A.CG_SISTEMA = E.SG_SISTEMA OR A.CG_SISTEMA IS NULL)");
	      query.append("  AND (A.CG_MODULO = E.SG_MODULO OR A.CG_MODULO IS NULL) ");
	      query.append("  AND (E.CD_MODALIDADE = A.CD_MODALIDADE)");
	      query.append("  AND (E.CD_MODALIDADE = M.CD_MODALIDADE)");
	      query.append("  AND (E.CD_MOEDA = C.CD_MOEDA)");
	      query.append("  AND (A.CD_APLIC = 1)");
	      
	      
	      if (extractFilter.getCdEmpresa() == null
	          || (extractFilter.getCdEmpresa() != null && extractFilter.getCdEmpresa().equals(101101))) {
	        query.append("    AND ( E.CD_EMPRESA = 101101 OR E.CD_EMPRESA BETWEEN 300000 and 499999 ) ");
	      } else {
	        query.append("    AND ( E.CD_EMPRESA = " + extractFilter.getCdEmpresa() + ")");
	      }
	      
	      if (extractFilter.getCdCliente() != null && extractFilter.getCdCliente().trim().length() > 0) {
	        query.append(" AND E.CD_CLIENTE = " + extractFilter.getCdCliente());
	      }
	      
	      if (extractFilter.getCdModalidade() != null && extractFilter.getCdModalidade().trim().length() > 0) {
	        query.append(" AND E.CD_MODALIDADE = " + extractFilter.getCdModalidade());
	      }
	    }else {
	      
	      query.append("SELECT CAST (COUNT(*)AS INTEGER) QUANTIDADE FROM( ");
	      query.append(" SELECT ");
	      
	      query.append(" COUNT(*)");
	      
	      query.append("  FROM EXTRATO_APP.TEX_EXTRATO E, EXTRATO_APP.TBL_MOD_APLIC A, EXTRATO_APP.TBDW_DIM_MOE C, EXTRATO_APP.TBDW_DIM_MODALIDADE M ");
	      
	      query.append("  WHERE 1=1 ");
	      
	      query.append("  AND (A.II_TIPO_CONTABIL = E.II_TIPO_CONTABIL OR A.II_TIPO_CONTABIL IS NULL)");
	      query.append("  AND (A.DS_OBN = E.DS_OBN OR A.DS_OBN IS NULL)");
	      query.append("  AND (A.NM_INDEXADOR = E.NM_INDEXADOR OR A.NM_INDEXADOR IS NULL)");
	      query.append("  AND (A.CD_GARANTIA = E.CD_GARANTIA OR A.CD_GARANTIA IS NULL)");
	      
	      query.append("  AND (A.CG_SISTEMA = E.SG_SISTEMA OR A.CG_SISTEMA IS NULL)");
	      query.append("  AND (A.CG_MODULO = E.SG_MODULO OR A.CG_MODULO IS NULL) ");
	      query.append("  AND (E.CD_MODALIDADE = A.CD_MODALIDADE)");
	      query.append("  AND (E.CD_MODALIDADE = M.CD_MODALIDADE)");
	      query.append("  AND (E.CD_MOEDA = C.CD_MOEDA)");
	      query.append("  AND (A.CD_APLIC = 1)");
	      
	      if (extractFilter.getCdEmpresa() == null
	          || (extractFilter.getCdEmpresa() != null && extractFilter.getCdEmpresa().equals(101101))) {
	        query.append("    AND ( E.CD_EMPRESA = 101101 OR E.CD_EMPRESA BETWEEN 300000 and 499999 ) ");
	      } else {
	        query.append("    AND ( E.CD_EMPRESA = " + extractFilter.getCdEmpresa() + ")");
	      }
	      
	      if (extractFilter.getCdCliente() != null && extractFilter.getCdCliente().trim().length() > 0) {
	        query.append(" AND E.CD_CLIENTE = " + extractFilter.getCdCliente());
	      }
	      
	      if (extractFilter.getCdModalidade() != null && extractFilter.getCdModalidade().trim().length() > 0) {
	        query.append(" AND E.CD_MODALIDADE = " + extractFilter.getCdModalidade());
	      }
	      
	      query.append(" GROUP BY ");
	      query.append(" E.CD_EMPRESA,  E.CD_CLIENTE,   E.NM_CLIENTE,   E.CD_CGCCPF,   E.DC_END1, E.DC_END2, E.DC_END3,");
	      query.append(" E.CD_PAIS, E.NOME_AOSCUIDADOS,  E.DS_OBN,       E.NM_EMPRESA,   E.CD_FILIAL,   E.NM_INDEXADOR,  E.CD_GARANTIA,");
	      query.append(" E.DT_INICIO_OPER, E.DT_VENCIMENTO_OPER, M.CD_GRPROD, M.CD_FAMPROD,");
	      query.append(" E.CD_MOEDA,  E.II_TIPO_CONTABIL, E.CD_CGCCPF, E.CD_MODALIDADE, E.CD_CONTRATO, E.DT_REFERENCIA, E.DT_INICIO_OPER, E.DT_VENCIMENTO_OPER,");
	      query.append(" E.CD_GRPROD, E.SG_SISTEMA, E.SG_MODULO,");
	      query.append(" A.DS_NEGOCIO, A.DS_NEGOCIO_INGLES, A.DISPLAY_QTDE,");
	      query.append(" C.CD_MOEDA_ISO,");
	      query.append(" C.NM_MOEDA,");
	      query.append(" M.DS_MODALIDADE, M.CD_FAMPROD)");
	      
	    }
	    
	    Object  data = em.createNativeQuery(query.toString()).getSingleResult();
	    
	    return Long.parseLong(data.toString());
	  }
	
	public Date getRefereceDate() {
    	StringBuilder query = new StringBuilder();
    	query.append(" SELECT DISTINCT DT_REFERENCIA FROM TEX_EXTRATO ");
    	return (Date) em.createNativeQuery(query.toString()).getSingleResult();
	}
	
}

	class ExtractRowMapper implements RowMapper<TexExtratoDTO> {

		@Override
		public TexExtratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TexExtratoDTO dto = new TexExtratoDTO();
			dto.setCdEmpresa(rs.getLong("CD_EMPRESA"));
			dto.setCdCliente(rs.getString("CD_CLIENTE"));
			dto.setNmCliente(rs.getString("NM_CLIENTE"));
		    dto.setCdCgcCpf(rs.getString("CD_CGCCPF") != null ? rs.getString("CD_CGCCPF") : "");
		    dto.setDcEnd1(rs.getString("DC_END1") != null? rs.getString("DC_END1"):"");
		    dto.setDcEnd2(rs.getString("DC_END2")!= null? rs.getString("DC_END2"):"");
		    dto.setDcEnd3(rs.getString("DC_END3") != null? rs.getString("DC_END3"):"");
			dto.setDsNegocio("DS_NEGOCIO");
			dto.setDsNegocioIngles("DS_NEGOCIO_INGLES");
			dto.setCdModalidade(rs.getLong("CD_MODALIDADE"));
			dto.setDsModalidade(rs.getString("DS_MODALIDADE"));
			dto.setCdContrato(rs.getString("CD_CONTRATO"));
			dto.setDtReferencia(rs.getDate("DT_REFERENCIA"));
			dto.setDtInicioOper(rs.getDate("DT_INICIO_OPER"));
			dto.setDtVencimentoOper(rs.getDate("DT_VENCIMENTO_OPER"));
			dto.setCdGrprod(rs.getLong("CD_GRPROD"));
			dto.setCdFamprod(rs.getLong("CD_FAMPROD"));
			dto.setCdProduto(rs.getLong("CD_PRODUTO"));
			dto.setSgSistema(rs.getString("SG_SISTEMA"));
			dto.setSgModulo(rs.getString("SG_MODULO"));
			dto.setCdMoeda(rs.getInt("CD_MOEDA"));
			dto.setQtde(rs.getDouble("QUANTIDADE"));
			dto.setIiTipoContabil(rs.getString("II_TIPO_CONTABIL"));
			dto.setCdPais(rs.getLong("CD_PAIS"));
			dto.setNomeAosCuidados(rs.getString("NOME_AOSCUIDADOS"));
			dto.setDsObn(rs.getString("DS_OBN"));
			dto.setNmEmpresa(rs.getString("NM_EMPRESA"));
			dto.setCdFilial(rs.getLong("CD_FILIAL"));
			dto.setNmIndexador(rs.getString("NM_INDEXADOR"));
			dto.setCdGarantia(rs.getString("CD_GARANTIA"));
			dto.setMoeda(rs.getString("CD_MOEDA_ISO"));
			dto.setDsProduto(rs.getString("DS_PRODUTO"));
			
			dto.setVlContabil(rs.getDouble("VL_CONTABIL"));
			dto.setVlCorrigidoMe(rs.getDouble("VL_CORRIGIDO_ME"));
			dto.setVlJurosMe(rs.getDouble("VL_JUROS_ME"));
			dto.setVlJurosMn(rs.getDouble("VL_JUROS_MN"));
			dto.setDtLiquidacaoOper(rs.getDate("DT_LIQUIDACAO_OPER") != null? new Date(rs.getDate("DT_LIQUIDACAO_OPER").getTime()) : null);
			
			
			dto.setDisplayQtde(rs.getString("DISPLAY_QTDE") != null && rs.getString("DISPLAY_QTDE") .equals("S")? "S":"N");
			dto.setVlOperacaoMe(rs.getDouble("VL_OPERACAO_ME"));
			dto.setVlOperacaoMn(rs.getDouble("VL_OPERACAO_MN"));
			dto.setVlOperacaoEmp(rs.getDouble("VL_OPERACAO_EMP"));
		    dto.setDisplaySldMn(rs.getString("DISPLAY_MN") != null && rs.getString("DISPLAY_MN").equals("S") ? "S" : "N");
		    dto.setDisplaySldMe(rs.getString("DISPLAY_ME") != null && rs.getString("DISPLAY_ME").equals("S") ? "S" : "N");
		    dto.setCdOperOrigem(rs.getString("CD_OPER_ORIGEM"));
			return dto;
		}
	
	}
	
	class ExtractRowMapperExtraxt implements RowMapper<TexExtratoDTO> {

	    private ReportType reportType;
	  
		public ExtractRowMapperExtraxt(ReportType reportType) {
		  this.reportType = reportType;
		}

    @Override
		public TexExtratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			TexExtratoDTO dto = new TexExtratoDTO();
			dto.setCdCliente(rs.getString("CD_CLIENTE"));
			dto.setNmCliente(rs.getString("NM_CLIENTE"));
			dto.setDcEnd1(rs.getString("DC_END1") != null? rs.getString("DC_END1"):"");
			dto.setDcEnd2(rs.getString("DC_END2")!= null? rs.getString("DC_END2"):"");
			dto.setDcEnd3(rs.getString("DC_END3") != null? rs.getString("DC_END3"):"");
			dto.setCdCgcCpf(rs.getString("CD_CGCCPF") != null ? rs.getString("CD_CGCCPF") : "");
			dto.setDtReferencia(rs.getDate("DT_REFERENCIA"));
			dto.setCdFamprod(rs.getLong("CD_PRODUTO"));
			dto.setDsProduto(getDsProduto(rs, reportType));
			dto.setMoeda(rs.getString("CD_MOEDA_ISO"));
			dto.setCdMoeda(rs.getInt("CD_MOEDA"));
			dto.setDisplaySldMn(rs.getString("display_mn"));
			dto.setDisplaySldMe(rs.getString("display_me"));
			dto.setCdModalidade(rs.getLong("CD_MODALIDADE"));
			dto.setQtde(rs.getString("display_qtde").equals("S")?rs.getDouble("QUANTIDADE"): null);
			dto.setVlOperacaoMe(rs.getDouble("VL_OPERACAO_ME"));
			dto.setVlOperacaoMn(rs.getDouble("VL_OPERACAO_MN"));
			return dto;
		}
		
		public String getDsProduto(ResultSet rs, ReportType isAnalytic) throws SQLException{
	        
	        if (rs.getInt("CD_MODALIDADE") == 94  ||  rs.getInt("CD_MODALIDADE") == 95){
	            if (ReportType.ANALYTIC.ordinal() == isAnalytic.ordinal()){
	                return  "[" + rs.getString("SG_SISTEMA") + "/" + rs.getString("SG_MODULO")  + "] - " + 
	                        "(" + rs.getInt("CD_PRODUTO")  + "/" + rs.getInt("CD_MODALIDADE") + ") - " + rs.getString("NM_CLIENTE");
	            }else{
	                return rs.getString("NM_CLIENTE");
	            }
	        }else{
	            if (ReportType.ANALYTIC.ordinal() == isAnalytic.ordinal()){
	                return  "[" + rs.getString("SG_SISTEMA") + "/" + rs.getString("SG_MODULO") + "] - " +
	                        "(" + rs.getInt("CD_PRODUTO") + "/" + rs.getInt("CD_MODALIDADE") + ") - " + 
	                        (!Strings.isBlank(rs.getString("DS_NEGOCIO"))?rs.getString("DS_NEGOCIO"):"") + " / " + (!Strings.isBlank(rs.getString("DS_NEGOCIO_INGLES"))?rs.getString("DS_NEGOCIO_INGLES"):"");
	            }else{
	                if (rs.getString("DS_NEGOCIO_INGLES") != null && rs.getString("DS_NEGOCIO_INGLES").trim().startsWith("*")){
	                    return  (!Strings.isBlank(rs.getString("DS_NEGOCIO"))?rs.getString("DS_NEGOCIO"):"") + " / " + (!Strings.isBlank(rs.getString("DS_NEGOCIO_INGLES"))?rs.getString("DS_NEGOCIO_INGLES"):"") +
	                    "\n\r * " + rs.getDate("DT_INICIO_OPER") + " - " + rs.getDate("DT_VENCIMENTO_OPER");
	                }else{
	                    return (!Strings.isBlank(rs.getString("DS_NEGOCIO"))?rs.getString("DS_NEGOCIO"):"") + " / " + (!Strings.isBlank(rs.getString("DS_NEGOCIO_INGLES"))?rs.getString("DS_NEGOCIO_INGLES"):"");
	                }
	            }
	        }
	    }
	
	}