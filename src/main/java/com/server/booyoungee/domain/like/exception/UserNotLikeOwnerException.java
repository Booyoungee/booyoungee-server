package com.server.booyoungee.domain.like.exception;

import static com.server.booyoungee.domain.like.exception.LikeExceptionCode.USER_NOT_LIKE_OWNER;

import com.server.booyoungee.global.exception.CustomException;

public class UserNotLikeOwnerException extends CustomException {
	public UserNotLikeOwnerException() {
		super(USER_NOT_LIKE_OWNER);
	}
}
