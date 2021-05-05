package com.db.extrato.dto;

import java.io.Serializable;
import java.util.Date;

import com.db.extrato.domain.extract.TbdwDmPessoa;
import com.db.extrato.util.Constants;

import lombok.Data;

@Data
public class TbdwDmPessoaDTO implements Serializable{
  
  private static final long serialVersionUID = -3128975636717802121L;

  private Long cdPessoa;
  
  private String nmPessoa;

  private String nmPessoaAbrev;

  private Long cdGecBr;

  private String nmGecBr;

  private Long cdAteBr;

  private String nmAteBr;
  
  private Long cdPessoaCcdb;

  private Long cdAteAl;
  
  private String nmAteAl;
  
  private Long cdPais;
  
  private String flEmpFundoDb;
  
  private Long cdPessoaBuba;
  
  private String flTipoPessoa;
  
  private String cdGcgCpf;
  
  private Long cdCosif;
  
  private Long cdNace;
  
  private Date dtInclusao;
  
  private Double vlPtrmLqd;
  
  private Double vlPrevMvt;
  
  private String flSensibilidade;
  
  private Long cdTpEmpresa;
  
  private Long cdCatgPessoa;
  
  private Long codPaisOrigem;
  
  private String codSitPessoa;
  
  private String indPep;
  
  private Double vlTotAtivos;
  
  private Double vlFaturamento;
  
  private Double vlRecOperacional;
  
  private Double vlOutrCapFinan;
  
  private String flCapFinanc;
  
  private String cdParagon;
  
  private String flBenefFinal;
  
  private Long cdContratoGlobal;
  
  private String cdParagonGrupo;
  
  private String flRatingParagon;
  
  private String flRatingGrupoParagon;
  
  private String flRatingBacen;
  
  private String flRatingGrupoBacen;

  private Date dtRatingGrupoParagon;
  
  private Date dtRatingParagon;
  
  private String flRatingArratoBacen;
  
  private String flRatingArratoParagon;
  
  private String dsLogradouro;
  
  private String dsCidade;
  
  private String cdUf;
  
  private Long cdCep;
  
  private Date dtUpdate;
  
  private String flCgd;
  
  private String nrLogradouro;
  
  private String dsComplLogr;
  
  private String dsBairro;
  
  private String telefonePessoa;
  
  private Date dataAtualizacaoRenda;
  
  private Date dataFimRelacionamentoConta;
  
  private Long cdAssessor;
  
  private String nmAssessor;
  
  private String emailAssessor;
  
  public static TbdwDmPessoaDTO convertDto(TbdwDmPessoa pessoa) {
    
    TbdwDmPessoaDTO pessoaDto = new TbdwDmPessoaDTO();
    
    pessoaDto.cdPessoa = pessoa.getCdPessoa();
    
    pessoaDto.nmPessoa = pessoa.getNmPessoa();
    
    pessoaDto.nmPessoaAbrev = pessoa.getNmPessoaAbrev();
    
    pessoaDto.cdGecBr = pessoa.getCdGecBr();

    pessoaDto.nmGecBr = pessoa.getNmGecBr();

    pessoaDto.cdAteBr = pessoa.getCdAteBr();

    pessoaDto.nmAteBr = pessoa.getNmAteBr();
    
    pessoaDto.cdPessoaCcdb = pessoa.getCdPessoaCcdb();

    pessoaDto.cdAteAl = pessoa.getCdAteAl();
    
    pessoaDto.nmAteAl = pessoa.getNmAteAl();
    
    pessoaDto.cdPais = pessoa.getCdPais();
    
    pessoaDto.flEmpFundoDb = pessoa.getFlEmpFundoDb();
    
    pessoaDto.cdPessoaBuba = pessoa.getCdPessoaBuba();
    
    pessoaDto.flTipoPessoa = pessoa.getFlTipoPessoa();
    
    pessoaDto.cdGcgCpf = (pessoa.getCdGcgCpf() == null ? Constants.SPACE_1 : pessoa.getCdGcgCpf().toString());
    
    pessoaDto.cdCosif = pessoa.getCdCosif();
    
    pessoaDto.cdNace = pessoa.getCdNace();
    
    pessoaDto.dtInclusao = pessoa.getDtInclusao();
    
    pessoaDto.vlPtrmLqd = pessoa.getVlPtrmLqd();
    
    pessoaDto.vlPrevMvt = pessoa.getVlPrevMvt();
    
    pessoaDto.flSensibilidade = pessoa.getFlSensibilidade();
    
    pessoaDto.cdTpEmpresa = pessoa.getCdTpEmpresa();
    
    pessoaDto.cdCatgPessoa = pessoa.getCdCatgPessoa();
    
    pessoaDto.codPaisOrigem = pessoa.getCodPaisOrigem();
    
    pessoaDto.codSitPessoa = pessoa.getCodSitPessoa();
    
    pessoaDto.indPep = pessoa.getIndPep();
    
    pessoaDto.vlTotAtivos = pessoa.getVlTotAtivos();
    
    pessoaDto.vlFaturamento = pessoa.getVlFaturamento();
    
    pessoaDto.vlRecOperacional = pessoa.getVlRecOperacional();
    
    pessoaDto.vlOutrCapFinan = pessoa.getVlOutrCapFinan();
    
    pessoaDto.flCapFinanc = pessoa.getFlCapFinanc();
    
    pessoaDto.cdParagon = pessoa.getCdParagon();
    
    pessoaDto.flBenefFinal = pessoa.getFlBenefFinal();
    
    pessoaDto.cdContratoGlobal = pessoa.getCdContratoGlobal();
    
    pessoaDto.cdParagonGrupo = pessoa.getCdParagonGrupo();
    
    pessoaDto.flRatingParagon = pessoa.getFlRatingParagon();
    
    pessoaDto.flRatingGrupoParagon = pessoa.getFlRatingGrupoParagon();
    
    pessoaDto.flRatingBacen = pessoa.getFlRatingBacen();
    
    pessoaDto.flRatingGrupoBacen = pessoa.getFlRatingGrupoBacen();

    pessoaDto.dtRatingGrupoParagon = pessoa.getDtRatingGrupoParagon();
    
    pessoaDto.dtRatingParagon = pessoa.getDtRatingParagon();
    
    pessoaDto.flRatingArratoBacen = pessoa.getFlRatingArratoBacen();
    
    pessoaDto.flRatingArratoParagon = pessoa.getFlRatingArratoParagon();
    
    pessoaDto.dsLogradouro = pessoa.getDsLogradouro();
    
    pessoaDto.dsCidade = pessoa.getDsCidade();
    
    pessoaDto.cdUf = pessoa.getCdUf();
    
    pessoaDto.cdCep = pessoa.getCdCep();
    
    pessoaDto.dtUpdate = pessoa.getDtUpdate();
    
    pessoaDto.flCgd = pessoa.getFlCgd();
    
    pessoaDto.nrLogradouro = pessoa.getNrLogradouro();
    
    pessoaDto.dsComplLogr = pessoa.getDsComplLogr();
    
    pessoaDto.dsBairro = pessoa.getDsBairro();
    
    pessoaDto.telefonePessoa = pessoa.getTelefonePessoa();
    
    pessoaDto.dataAtualizacaoRenda = pessoa.getDataAtualizacaoRenda();
    
    pessoaDto.dataFimRelacionamentoConta = pessoa.getDataFimRelacionamentoConta();
    
    pessoaDto.cdAssessor = pessoa.getCdAssessor();
    
    pessoaDto.nmAssessor = pessoa.getNmAssessor();
    
    pessoaDto.emailAssessor = pessoa.getCodSitPessoa();
    return pessoaDto;
    
  }
  
}
