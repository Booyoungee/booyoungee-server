package com.server.booyoungee.domain.stamp.exception;

import static com.server.booyoungee.domain.stamp.exception.StampExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class DuplicateStampException extends CustomException {

	public DuplicateStampException() {
		super(DUPLICATE_STAMP);
	}
}


