package com.server.booyoungee.domain.review.stars.exception;

import static com.server.booyoungee.domain.review.stars.exception.StarExceptionCode.INVALID_STAR_VALUE;

import com.server.booyoungee.global.exception.CustomException;

public class InvalidStarValueException extends CustomException {
	public InvalidStarValueException() {
		super(INVALID_STAR_VALUE);
	}
}
