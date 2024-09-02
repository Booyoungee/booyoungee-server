package com.server.booyoungee.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record NickNameRequest(

        @Schema(description = "닉네임", example = "홍길동", requiredMode = REQUIRED)
        @NotNull
        String nickname

)
{

}
