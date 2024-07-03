package com.server.booyoungee.domain.tourInfo.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.tourInfo.dao.TourInfoRepository;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourInfoService {
	private final TourInfoRepository tourInfoRepository;

	public void viewContent(String contentId) {
		TourInfo tourInfo = findById(contentId);
		if (tourInfo == null) {
			saveContent(contentId);
		}else {
			tourInfo.increaseViewCount();
			tourInfoRepository.save(tourInfo);
		}
	}

	public TourInfoResponseDto getTourInfo(String contentId) {
		TourInfo tourInfo = findById(contentId);
		return TourInfoResponseDto.builder()
			.contentId(tourInfo.getContentId())
			.views(tourInfo.getViews())
			.build();
	}

	public List<TourInfoResponseDto> getTourInfoList() {
		return tourInfoRepository.findAll().stream()
			.map(tourInfo -> TourInfoResponseDto.builder()
				.contentId(tourInfo.getContentId())
				.views(tourInfo.getViews())
				.build())
			.toList();
	}

	private void saveContent(String contentId) {
		TourInfo tourInfo = TourInfo.builder()
			.contentId(contentId)
			.views(1L)
			.build();
		tourInfoRepository.save(tourInfo);
	}

	private TourInfo findById(String contentId) {
		return tourInfoRepository.findById(contentId).orElse(null);
	}
}
