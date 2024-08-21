package com.server.booyoungee.domain.review.stars.domain;

import com.server.booyoungee.domain.review.stars.exception.InvalidStarValueException;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Stars {
	private int stars;

	public void validateStar() {
		if (stars < 1 || stars > 5) {
			throw new InvalidStarValueException();
		}
	}
}
