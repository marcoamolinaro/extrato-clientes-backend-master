package com.db.extrato.enums;

public enum DerivativeReport {

	CDB_PENALTY("/relatorios/derivativos/subreports/Extract_Derivatives_CDB_Penalty.jasper"),
	NDF("/relatorios/derivativos/subreports/Extract_Derivatives_NDF.jasper"),
	OPCOES("/relatorios/derivativos/subreports/Extract_Derivatives_Opcoes.jasper"),
	RENDA_FIXA("/relatorios/derivativos/subreports/Extract_Derivatives_RendaFixa.jasper"),
	SWAP_FLUXO("/relatorios/derivativos/subreports/Extract_Derivatives_Swap_Fluxo.jasper"),
	SWAP("/relatorios/derivativos/subreports/Extract_Derivatives_Swap.jasper"),
	TERMOS("/relatorios/derivativos/subreports/Extract_Derivatives_Termos.jasper"),
	SWAP_XLS("/relatorios/derivativos/excel/ExtractDerivativeSwapReport.xlsx"),
	SWAP_FLUXO_XLS("/relatorios/derivativos/excel/ExtractDerivativeSwapFluxoReport.xlsx"),
	NDF_XLS("/relatorios/derivativos/excel/ExtractDerivativeNDFReport.xlsx"),
	OPCOES_XLS("/relatorios/derivativos/excel/ExtractDerivativeOpcoesReport.xlsx"),
	TERMOS_XLS("/relatorios/derivativos/excel/ExtractDerivativeTermosReport.xlsx"),
	RENDA_FIXA_XLS("/relatorios/derivativos/excel/ExtractDerivativeRendaFixaReport.xlsx"),
	CDB_PENALTY_XLS("/relatorios/derivativos/excel/ExtractDerivativeCDBPenaltyReport.xlsx");
	
	private String path;

	private DerivativeReport(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
}
