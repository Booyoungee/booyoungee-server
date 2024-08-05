package com.server.booyoungee.domain.place.exception.movie;

import static com.server.booyoungee.domain.place.exception.movie.MoviePlaceExceptionCode.NOT_FOUND_MOVIE_PLACE;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundMoviePlaceException extends CustomException {
	public NotFoundMoviePlaceException() {
		super(NOT_FOUND_MOVIE_PLACE);
	}
}
