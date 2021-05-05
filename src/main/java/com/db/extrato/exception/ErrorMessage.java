package com.db.extrato.exception;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorMessage {
	
	private int status;
	private Date timestamp;
	private String message;
	
	public ErrorMessage(Date timestamp, int status, String message) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
	}

}
