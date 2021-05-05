package com.db.extrato.util;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 608555441472918050L;
	
	@Value("${com.db.extrato.time.limit.report}")
	private Integer timelimit;

	public Integer getTimelimit() {
		return timelimit;
	}

}
