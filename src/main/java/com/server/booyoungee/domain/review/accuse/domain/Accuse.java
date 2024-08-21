package com.server.booyoungee.domain.review.accuse.domain;

import static java.time.LocalDateTime.now;

import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accuse extends BaseTimeEntity {

	private Long userId;

	public Accuse(Long userId) {
		this.userId = userId;
		this.createdAt = now();
		this.updatedAt = now();
	}
}
