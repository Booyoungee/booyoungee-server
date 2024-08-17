package com.server.booyoungee.domain.bookmark.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;
@Getter
@AllArgsConstructor
public enum BookMarkExceptionCode implements ExceptionCode{

    NOT_FOUND_BOOKMARK(NOT_FOUND, "해당 북마크를 찾을 수 없습니다."),
    DUPLICATE_BOOKMARK(CONFLICT, "이미 북마크에 추가된 장소입니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
