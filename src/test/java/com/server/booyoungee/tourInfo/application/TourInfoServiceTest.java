package com.server.booyoungee.tourInfo.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.mock.FakeTourInfoRepository;

public class TourInfoServiceTest {

	private TourInfoService tourInfoService;
	private FakeTourInfoRepository fakeTourInfoRepository;

	@BeforeEach
	void setUp() {
		fakeTourInfoRepository = new FakeTourInfoRepository();
		tourInfoService = new TourInfoService(fakeTourInfoRepository);
	}
/*
	@Test
	void 새롭게_조회한_관광지_정보의_조회수는_1이다() {
		// given
		String contentId = "123";

		// when
		tourInfoService.viewContent(contentId);

		// then
		TourInfo tourInfo = fakeTourInfoRepository.findById(contentId).orElse(null);
		assertNotNull(tourInfo);
		assertEquals(contentId, tourInfo.getContentId());
		assertEquals(1L, tourInfo.getViews());
	}*/

	@Test
	void 이전에_조회했던_관광지_정보를_조회할_수_있다() {
		// given
		String contentId = "123";
		TourInfo existingTourInfo = TourInfo.builder()
			.contentId(contentId)
			.views(10L)
			.build();
		fakeTourInfoRepository.save(existingTourInfo);

		// when
		tourInfoService.viewContent(contentId);

		// then
		TourInfo tourInfo = fakeTourInfoRepository.findById(contentId).orElse(null);
		assertNotNull(tourInfo);
		assertEquals(contentId, tourInfo.getContentId());
		assertEquals(11L, tourInfo.getViews());
	}
/*
	@Test
	void 관광지_정보를_조회할_수_있다() {
		// given
		String contentId = "123";
		TourInfo tourInfo = TourInfo.builder()
			.contentId(contentId)
			.views(10L)
			.build();
		fakeTourInfoRepository.save(tourInfo);

		// when
		TourInfoResponseDto tourInfoResponseDto = tourInfoService.getTourInfo(contentId);

		// then
		assertNotNull(tourInfoResponseDto);
		assertEquals(contentId, tourInfoResponseDto.getContentId());
		assertEquals(10L, tourInfoResponseDto.getViews());
	}

	@Test
	void 이전에_조회한_관광지_정보를_목록으로_볼_수_있다() {
		// given
		TourInfo tourInfo1 = TourInfo.builder()
			.contentId("123")
			.views(10L)
			.build();
		TourInfo tourInfo2 = TourInfo.builder()
			.contentId("456")
			.views(5L)
			.build();
		fakeTourInfoRepository.save(tourInfo1);
		fakeTourInfoRepository.save(tourInfo2);

		// when
		List<TourInfoResponseDto> tourInfoList = tourInfoService.getTourInfoList();

		// then
		assertNotNull(tourInfoList);
		assertEquals(2, tourInfoList.size());

		TourInfoResponseDto dto1 = tourInfoList.get(0);
		TourInfoResponseDto dto2 = tourInfoList.get(1);

		assertEquals("123", dto1.getContentId());
		assertEquals(10L, dto1.getViews());

		assertEquals("456", dto2.getContentId());
		assertEquals(5L, dto2.getViews());
	}*/
}
