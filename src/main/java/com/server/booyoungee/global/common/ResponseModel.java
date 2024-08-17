package com.server.booyoungee.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseModel<T> {

	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";

	private String status;
	private T data;
	private String message;


	public static <T> ResponseModel<T> success(T data) {
		return new ResponseModel<>(SUCCESS_STATUS, data);
	}

	public static ResponseModel<?> error(String message) {
		return new ResponseModel<>(ERROR_STATUS, null, message);
	}

	private ResponseModel(String status, T data) {
		this.status = status;
		this.data = data;
		this.message = "true";
	}
}

