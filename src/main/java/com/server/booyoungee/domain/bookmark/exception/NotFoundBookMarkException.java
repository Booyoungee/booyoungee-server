package com.server.booyoungee.domain.bookmark.exception;

import com.server.booyoungee.global.exception.CustomException;

import static com.server.booyoungee.domain.bookmark.exception.BookMarkExceptionCode.NOT_FOUND_BOOKMARK;

public class NotFoundBookMarkException extends CustomException {

    public NotFoundBookMarkException() {
        super(NOT_FOUND_BOOKMARK);
    }

}