package com.server.booyoungee.domain.user.dto.response;

public record UserPersistResponse(
        Long userId
) {
    public static UserPersistResponse of(Long userId) {
        return new UserPersistResponse(userId);
    }
}
