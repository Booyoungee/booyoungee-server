package com.server.booyoungee.domain.bookmark.exception;

import com.server.booyoungee.global.exception.CustomException;

import static com.server.booyoungee.domain.bookmark.exception.BookMarkExceptionCode.DUPLICATE_BOOKMARK;

public class DuplicateBookMarkException extends CustomException {

    public DuplicateBookMarkException() {
        super(DUPLICATE_BOOKMARK);
    }
}
