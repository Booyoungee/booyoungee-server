package com.server.booyoungee.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record NickNameResponse(
        @Schema(description = "닉네임", example = "홍길동", requiredMode = REQUIRED)
        String nickname
) {

    public static NickNameResponse of(String nickname) {
        return new NickNameResponse(nickname);
    }
}
