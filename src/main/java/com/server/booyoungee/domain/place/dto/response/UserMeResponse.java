package com.server.booyoungee.domain.place.dto.response;

import com.server.booyoungee.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserMeResponse (
        @Schema(description = "스탬프 보유 여부", example = "true", required = true)
        boolean hasStamp,

        @Schema(description = "좋아요 보유 여부", example = "true", required = true)
        boolean hasLike,

        @Schema(description = "북마크 보유 여부", example = "true", required = true)
        boolean hasBookmark
){
    public static UserMeResponse of(boolean hasStamp, boolean hasLike, boolean hasBookmark) {
        return new UserMeResponse(hasStamp, hasLike, hasBookmark);
    }
}
