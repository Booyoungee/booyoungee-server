package com.server.booyoungee.domain.BookMark.exception;

import com.server.booyoungee.global.exception.CustomException;

import static com.server.booyoungee.domain.BookMark.exception.BookMarkExceptionCode.NOT_FOUND_BOOKMARK;
import static com.server.booyoungee.domain.place.exception.movie.MoviePlaceExceptionCode.NOT_FOUND_MOVIE_PLACE;

public class NotFoundBookMarkException extends CustomException {

    public NotFoundBookMarkException() {
        super(NOT_FOUND_BOOKMARK);
    }

}