package com.server.booyoungee.domain.like.exception;

import static com.server.booyoungee.domain.like.exception.LikeExceptionCode.NOT_FOUND_LIKE;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundLikeException extends CustomException {
	public NotFoundLikeException() {
		super(NOT_FOUND_LIKE);
	}
}
