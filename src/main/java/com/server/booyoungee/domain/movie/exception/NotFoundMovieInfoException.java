package com.server.booyoungee.domain.movie.exception;

import static com.server.booyoungee.domain.movie.exception.MovieInfoExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundMovieInfoException extends CustomException {

	public NotFoundMovieInfoException() {
		super(NOT_FOUND_MOVIE_INFO);
	}
}
