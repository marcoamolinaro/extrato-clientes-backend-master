package com.db.extrato.dto;

import java.io.Serializable;

import com.db.extrato.domain.extract.TbclParametroExt;

import lombok.Data;

@Data
public class TbclParametroExtDTO implements Serializable {

  private static final long serialVersionUID = -7718910097111570328L;

  private Long cdSeqModalidade;

  private Long cdModalidade;

  private String cdSisOrig;

  private int qtCasaDec;

  private String idDTrro;

  private String iddDTrro;

  private String idDCamd;

  private String iddDCamd;

  private String idDCaex;

  private String iddDCaex;

  private String idDSnop;

  private String iddDSnop;

  private Long cdProduto;

  private Long cdMoedaOper;

  public static TbclParametroExtDTO convertDto(TbclParametroExt paramModality) {

    TbclParametroExtDTO paramModalityDto = new TbclParametroExtDTO();

    paramModalityDto.cdSeqModalidade = paramModality.getCdSeqModalidade();
    paramModalityDto.cdModalidade = paramModality.getCdModalidade();
    paramModalityDto.cdSisOrig = paramModality.getCdSisOrig();
    paramModalityDto.qtCasaDec = paramModality.getQtCasaDec();
    paramModalityDto.idDTrro = paramModality.getIdDTrro();
    paramModalityDto.iddDTrro = paramModality.getIddDTrro();
    paramModalityDto.idDCamd = paramModality.getIdDCamd();
    paramModalityDto.iddDCamd = paramModality.getIddDCamd();
    paramModalityDto.idDCaex = paramModality.getIdDCaex();
    paramModalityDto.iddDCaex = paramModality.getIddDCaex();
    paramModalityDto.idDSnop = paramModality.getIdDSnop();
    paramModalityDto.iddDSnop = paramModality.getIddDSnop();
    paramModalityDto.cdProduto = paramModality.getCdProduto();
    paramModalityDto.cdMoedaOper = paramModality.getCdMoedaOper();
    return paramModalityDto;
  }

}
