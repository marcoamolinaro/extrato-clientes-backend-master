package com.db.extrato.repository.extract.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.db.extrato.controller.filter.AutExtExtractFilter;
import com.db.extrato.dto.AutExtTexExtratoDTO;
import com.db.extrato.enums.AutDsTipoOperacao;
import com.db.extrato.enums.AutStatus;
import com.db.extrato.enums.TipoContabil;

@Repository
public class AutExtTexExtratoRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Page<AutExtTexExtratoDTO> findPage(AutExtExtractFilter autExtExtractFilter, Pageable page) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(" A.AUT_CD_SEQUENCIAL, A.AUT_DS_TIPO_OPERACAO, A.AUT_STATUS, A.AUT_CD_USUARIO_SOLICITACAO, A.AUT_DT_SOLICITACAO, A.AUT_CD_USUARIO_AUTORIZACAO, A.AUT_DT_AUTORIZACAO, A.OLD_CD_EMPRESA, A.OLD_CD_CLIENTE, A.OLD_CD_MODALIDADE, A.OLD_CD_OPER_ORIGEM, A.CD_EMPRESA, A.CD_CLIENTE, A.CD_MODALIDADE, A.CD_OPER_ORIGEM, A.CD_CONTRATO, A.DT_REFERENCIA, A.DT_INICIO_OPER, A.DT_VENCIMENTO_OPER, A.DT_LIQUIDACAO_OPER, A.CD_GRPROD, A.CD_FAMPROD, A.CD_PRODUTO, A.SG_SISTEMA, A.SG_MODULO, A.CD_MOEDA, A.QUANTIDADE, A.VL_CONTABIL, A.VL_CORRIGIDO_ME, A.VL_OPERACAO_ME, A.VL_OPERACAO_MN, A.VL_OPERACAO_EMP, A.VL_JUROS_ME, A.VL_JUROS_MN, A.II_TIPO_CONTABIL, A.CD_CGCCPF, A.NM_CLIENTE, A.DC_END1, A.DC_END2, A.DC_END3, A.CD_PAIS, A.NOME_AOSCUIDADOS, A.DS_OBN, A.NM_EMPRESA, A.CD_FILIAL, A.NM_INDEXADOR, A.CD_GARANTIA, A.CD_CGCCPF, A.VL_CORRIGIDO_ME ");
		query.append(" , MA.DS_NEGOCIO, MA.DS_NEGOCIO_INGLES ");
		query.append(" , M.DS_PRODUTO, M.DS_MODALIDADE ");
		query.append(" , ME.NM_MOEDA ");
		query.append(" FROM EXTRATO_APP.AUT_EXT_TEX_EXTRATO A ");
		query.append(" LEFT JOIN EXTRATO_APP.TBL_MOD_APLIC MA ON A.CD_MODALIDADE = MA.CD_MODALIDADE AND A.DS_OBN = MA.DS_OBN ");
		query.append(" LEFT JOIN EXTRATO_APP.TBDW_DIM_MODALIDADE M ON A.CD_MODALIDADE = M.CD_MODALIDADE ");
		query.append(" LEFT JOIN EXTRATO_APP.TBDW_DIM_MOE ME ON A.CD_MOEDA = ME.CD_MOEDA ");
	    query.append(" WHERE 1=1 ");
	    if (autExtExtractFilter.getAutStatus() != null) {
	      query.append(" AND A.AUT_STATUS = '" + autExtExtractFilter.getAutStatus() + "'" );
	    }
	    if (autExtExtractFilter.getDtReferencia() != null) {
	      query.append(" AND TO_CHAR(A.DT_REFERENCIA,'yyyy-MM-dd') = '" + autExtExtractFilter.getDtReferencia() + "'");
	    }
		query.append(" OFFSET " + page.getOffset() + " ROWS FETCH NEXT " + page.getPageSize() + " ROWS ONLY");
		List<AutExtTexExtratoDTO> resultSet = jdbcTemplate.query(query.toString(), new RowMapper<AutExtTexExtratoDTO>() {
			@Override
			public AutExtTexExtratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AutExtTexExtratoDTO dto = new AutExtTexExtratoDTO();
				dto.setAutCdSequencial(rs.getLong("AUT_CD_SEQUENCIAL"));
				dto.setAutDsTipoOperacao(AutDsTipoOperacao.valueOf(rs.getString("AUT_DS_TIPO_OPERACAO")));
				dto.setAutStatus(AutStatus.valueOf(rs.getString("AUT_STATUS")));
				dto.setAutCdUsuarioSolicitacao(rs.getString("AUT_CD_USUARIO_SOLICITACAO"));
				dto.setAutDtSolicitacao(rs.getDate("AUT_DT_SOLICITACAO"));
				dto.setAutCdUsuarioAutorizacao(rs.getString("AUT_CD_USUARIO_AUTORIZACAO"));
				dto.setAutDtAutorizacao(rs.getDate("AUT_DT_AUTORIZACAO"));
				dto.setOldCdEmpresa(rs.getLong("OLD_CD_EMPRESA"));
				dto.setOldCdCliente(rs.getLong("OLD_CD_CLIENTE"));
				dto.setOldCdModalidade(rs.getLong("OLD_CD_MODALIDADE"));
				dto.setOldCdOperOrigem(rs.getString("OLD_CD_OPER_ORIGEM"));
				dto.setCdEmpresa(rs.getLong("CD_EMPRESA"));
				dto.setCdCliente(rs.getLong("CD_CLIENTE"));
				dto.setCdModalidade(rs.getLong("CD_MODALIDADE"));
				dto.setCdOperOrigem(rs.getString("CD_OPER_ORIGEM"));
				dto.setCdContrato(rs.getString("CD_CONTRATO"));
				dto.setDtReferencia(rs.getDate("DT_REFERENCIA"));
				dto.setDtInicioOper(rs.getDate("DT_INICIO_OPER"));
				dto.setDtVencimentoOper(rs.getDate("DT_VENCIMENTO_OPER"));
				dto.setDtLiquidacaoOper(rs.getDate("DT_LIQUIDACAO_OPER"));
				dto.setCdGrprod(rs.getLong("CD_GRPROD"));
				dto.setCdFamprod(rs.getLong("CD_FAMPROD"));
				dto.setCdProduto(rs.getLong("CD_PRODUTO"));
				dto.setSgSistema(rs.getString("SG_SISTEMA"));
				dto.setSgModulo(rs.getString("SG_MODULO"));
				dto.setCdMoeda(rs.getLong("CD_MOEDA"));
				dto.setQuantidade(rs.getDouble("QUANTIDADE"));
				dto.setVlContabil(rs.getDouble("VL_CONTABIL"));
				dto.setVlCorrigidoMe(rs.getDouble("VL_CORRIGIDO_ME"));
				dto.setVlOperacaoMe(rs.getDouble("VL_OPERACAO_ME"));
				dto.setVlOperacaoMn(rs.getDouble("VL_OPERACAO_MN"));
				dto.setVlOperacaoEmp(rs.getDouble("VL_OPERACAO_EMP"));
				dto.setVlJurosMe(rs.getDouble("VL_JUROS_ME"));
				dto.setVlJurosMn(rs.getDouble("VL_JUROS_MN"));
				String tipoContabil = rs.getString("II_TIPO_CONTABIL");
				dto.setIiTipoContabil(tipoContabil != null ? TipoContabil.valueOf(tipoContabil) : null);
				dto.setCdCgcCpf(rs.getString("CD_CGCCPF"));
				dto.setNmCliente(rs.getString("NM_CLIENTE"));
				dto.setDcEnd1(rs.getString("DC_END1"));
				dto.setDcEnd2(rs.getString("DC_END2"));
				dto.setDcEnd3(rs.getString("DC_END3"));
				dto.setCdPais(rs.getLong("CD_PAIS"));				
				dto.setNomeAosCuidados(rs.getString("NOME_AOSCUIDADOS"));
				dto.setDsObn(rs.getString("DS_OBN"));
				dto.setNmEmpresa(rs.getString("NM_EMPRESA"));
				dto.setCdFilial(rs.getLong("CD_FILIAL"));
				dto.setNmIndexador(rs.getString("NM_INDEXADOR"));
				dto.setCdGarantia(rs.getString("CD_GARANTIA"));
				dto.setDsNegocio(rs.getString("DS_NEGOCIO"));
				dto.setDsNegocioIngles(rs.getString("DS_NEGOCIO_INGLES"));
				dto.setDsModalidade(rs.getString("DS_PRODUTO"));
				dto.setDsProduto(rs.getString("DS_MODALIDADE"));
				dto.setNmMoeda(rs.getString("NM_MOEDA"));
				return dto;
			}
		});
		return new PageImpl<AutExtTexExtratoDTO>(
				resultSet, 
				page, 
				count(autExtExtractFilter));
	}
	
	public Long count(AutExtExtractFilter autExtExtractFilter) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ");
		query.append(" COUNT(1) ");
		query.append(" FROM EXTRATO_APP.AUT_EXT_TEX_EXTRATO A ");
		query.append(" LEFT JOIN EXTRATO_APP.TBL_MOD_APLIC MA ON A.CD_MODALIDADE = MA.CD_MODALIDADE AND A.DS_OBN = MA.DS_OBN ");
		query.append(" LEFT JOIN EXTRATO_APP.TBDW_DIM_MODALIDADE M ON A.CD_MODALIDADE = M.CD_MODALIDADE ");
		query.append(" LEFT JOIN EXTRATO_APP.TBDW_DIM_MOE ME ON A.CD_MOEDA = ME.CD_MOEDA ");
	    query.append(" WHERE 1=1 ");
	    if (autExtExtractFilter.getAutStatus() != null) {
	      query.append(" AND A.AUT_STATUS = '" + autExtExtractFilter.getAutStatus() + "'" );
	    }
	    if (autExtExtractFilter.getDtReferencia() != null) {
	      query.append(" AND TO_CHAR(A.DT_REFERENCIA,'yyyy-MM-dd') = '" + autExtExtractFilter.getDtReferencia() + "'");
	    }
	    return jdbcTemplate.queryForObject(query.toString(), Long.class);
	}

}