package com.server.booyoungee.tourInfo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.server.booyoungee.domain.tourInfo.domain.TourInfo;

public class TourInfoTest {

	@Test
	public void 조회수가_정상적으로_증가된다() {
		// Given
		TourInfo tourInfo = TourInfo.builder()
			.contentId("123456")
			.views(0L)
			.build();

		// When
		tourInfo.increaseViewCount();

		// Then
		assertEquals(1L, tourInfo.getViews());
	}
}
