package com.server.booyoungee.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";

	private String status;
	private T data;
	private String message;


	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(SUCCESS_STATUS, data);
	}

	public static ApiResponse<?> error(String message) {
		return new ApiResponse<>(ERROR_STATUS, null, message);
	}

	private ApiResponse(String status, T data) {
		this.status = status;
		this.data = data;
		this.message = "true";
	}
}

