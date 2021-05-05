package com.db.extrato.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbdwDmMoeDTO {

	private Long cdMoeda;

	private String cdMoedaIso;

	private String nmMoeda;

	private String tpParidadeDolar;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dtExclusaoPtax;

}
