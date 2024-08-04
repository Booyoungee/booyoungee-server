package com.server.booyoungee.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorMessage;
	private final String additionalInfo;  // Field to store additional info like ID

	public CustomException(ErrorCode errorMessage, String additionalInfo) {
		super(String.valueOf(errorMessage.getHttpStatus()));
		this.errorMessage = errorMessage;
		this.additionalInfo = additionalInfo;
	}

	public CustomException(ErrorCode errorMessage) {
		this(errorMessage, null);
	}

	public int getHttpStatusCode() {
		return errorMessage.getHttpStatus().value();
	}
}
