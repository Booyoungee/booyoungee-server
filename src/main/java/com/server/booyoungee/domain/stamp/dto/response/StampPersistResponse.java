package com.server.booyoungee.domain.stamp.dto.response;

public record StampPersistResponse(

	Long stampId

) {
	public static StampPersistResponse of(Long stampId) {
		return new StampPersistResponse(stampId);
	}
}
