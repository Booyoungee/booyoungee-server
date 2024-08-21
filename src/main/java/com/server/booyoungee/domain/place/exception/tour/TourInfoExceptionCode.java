package com.server.booyoungee.domain.place.exception.tour;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TourInfoExceptionCode implements ExceptionCode {

	OPEN_API_CALL_ERROR(BAD_REQUEST, "오픈 API 호출 중 오류가 발생했습니다."),
	LIST_PARSING_ERROR(BAD_REQUEST, "List 파싱 중 오류가 발생했습니다."),
	NOT_FOUND_TOUR_PLACE(BAD_REQUEST, "API에서  관광지 정보를 찾을 수 없습니다."),
	NOT_FOUND_TOUR_PLACE_BY_ID(NOT_FOUND, "DB에서 해당 ID의 관광지 정보를 찾을 수 없습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
