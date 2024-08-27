package com.server.booyoungee.domain.stamp.exception;

import static com.server.booyoungee.domain.stamp.exception.StampExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundStampException extends CustomException {
	public NotFoundStampException() {
		super(NOT_FOUND_STAMP);
	}
}
