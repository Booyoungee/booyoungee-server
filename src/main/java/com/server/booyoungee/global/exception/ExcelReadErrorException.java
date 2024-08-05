package com.server.booyoungee.global.exception;

import static com.server.booyoungee.global.exception.GlobalExceptionCode.EXCEL_READ_ERROR;

public class ExcelReadErrorException extends CustomException {
	public ExcelReadErrorException() {
		super(EXCEL_READ_ERROR);
	}
}
