package com.db.extrato.domain.extract;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.db.extrato.enums.ReportExecution;
import com.db.extrato.enums.ReportStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="EXTRACT_LOG_REPORT", schema = "EXTRATO_APP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames=true)
public class ExtractLogReport implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extractLogReport_seq")
	@SequenceGenerator(sequenceName = "SEQ_EXT_LOG", allocationSize = 1, name = "extractLogReport_seq")
	@Column(name = "LOG_ID")
	@EqualsAndHashCode.Include
	private Long logId;
	
	@Column(name = "COD_CLI")
	@NotNull
	private Long cdCli;
	
	@Column(name = "NM_CLI")
	private String nmCli;
	
	@Column(name = "AG_TIPO")
	private String agTipo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "EXECUCAO")
	private ReportExecution execucao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")	
	private ReportStatus status;
	
	@Column(name = "DESCRICAO")	
	private String descricao;
	
	@Column(name = "FILENAME")	
	private String filename;
	
	@Column(name = "DT_LOG")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm")
	@NotNull
	private Date dtLog;	
	
	@PrePersist
	protected void onCreate() {
		this.dtLog = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.dtLog = new Date();
	}
}
