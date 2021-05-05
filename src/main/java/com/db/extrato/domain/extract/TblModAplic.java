package com.db.extrato.domain.extract;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TBL_MOD_APLIC", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class TblModAplic implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tblModAplic_seq")
	@SequenceGenerator(sequenceName = "SEQ_TBL_MOD_APLIC", allocationSize = 1, name = "tblModAplic_seq")
	@Column(name = "CD_SEQ_MOD_APLIC")
	@EqualsAndHashCode.Include
	private Long cdSeqModAplic;
	
	@Column(name = "CD_APLIC")
	private Long cdApplication;
	
	@Column(name = "CD_MODALIDADE")
	private Long cdModalidade;
	
	@Column(name = "DS_NEGOCIO")
	private String dsNegocio;
	
	@Column(name = "DS_NEGOCIO_INGLES")
	private String dsNegocioIngles;
	
	@Column(name = "DISPLAY_QTDE")	
	private String displayQtde;
	
	@Column(name = "DISPLAY_ME")	
	private String displayMe;
	
	@Column(name = "DISPLAY_MN")	
	private String displayMn;
	
	@Column(name = "II_TIPO_CONTABIL")
	@Length(max = 1, message = "Campo deve ter tamanho 1 caracter")
	private String iiTipoContabil;
	
	@Column(name = "DS_OBN")
	private String dsObn;
	
	@Column(name = "CD_SIS_ORIG")
	private String cdSisOrig;
	
	@Column(name = "CG_SISTEMA")
	private String cgSistema;

	@Column(name = "CG_MODULO")	
	private String cgModulo;
	
	@Column(name = "NM_INDEXADOR")		
	private String nmIndexador;
	
	@Column(name = "CD_GARANTIA")		
	private String cdGarantia;
}
