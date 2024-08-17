package com.server.booyoungee.domain.BookMark.exception;

import com.server.booyoungee.global.exception.CustomException;

import static com.server.booyoungee.domain.BookMark.exception.BookMarkExceptionCode.DUPLICATE_BOOKMARK;
import static com.server.booyoungee.domain.BookMark.exception.BookMarkExceptionCode.NOT_FOUND_BOOKMARK;

public class DuplicateBookMarkException extends CustomException {

    public DuplicateBookMarkException() {
        super(DUPLICATE_BOOKMARK);
    }
}
