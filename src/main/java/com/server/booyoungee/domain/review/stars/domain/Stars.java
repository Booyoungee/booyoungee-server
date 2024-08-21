package com.server.booyoungee.domain.review.stars.domain;

import static lombok.AccessLevel.PROTECTED;

import com.server.booyoungee.domain.review.stars.exception.InvalidStarValueException;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Stars {
	private int stars;

	public static Stars of(int stars) {
		validateStar(stars);
		return Stars.builder()
				.stars(stars)
				.build();
	}

	public static void validateStar(int stars) {
		if (stars < 1 || stars > 5) {
			throw new InvalidStarValueException();
		}
	}
}
