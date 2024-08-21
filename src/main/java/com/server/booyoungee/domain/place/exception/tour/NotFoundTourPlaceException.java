package com.server.booyoungee.domain.place.exception.tour;

import com.server.booyoungee.global.exception.CustomException;

import static com.server.booyoungee.domain.place.exception.store.StorePlaceExceptionCode.NOT_FOUND_STORE_PLACE;
import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.NOT_FOUND_TOUR_PLACE;

public class NotFoundTourPlaceException extends CustomException {
    public NotFoundTourPlaceException() {
        super(NOT_FOUND_TOUR_PLACE);
    }
}
