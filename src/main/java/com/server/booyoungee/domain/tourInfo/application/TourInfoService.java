package com.server.booyoungee.domain.tourInfo.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.tourInfo.dao.TourInfoRepository;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourInfoService {
	private final TourInfoRepository tourInfoRepository;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	public void viewContent(String contentId) {
		TourInfo tourInfo = findById(contentId);
		if (tourInfo == null) {
			saveContent(contentId);
		} else {
			tourInfo.increaseViewCount();
			tourInfoRepository.save(tourInfo);
		}
	}

	public void viewContent(String contentId, String contentTypeId) {
		TourInfo tourInfo = findById(contentId, contentTypeId);
		if (tourInfo == null) {
			saveContent(contentId, contentTypeId);
		} else {
			tourInfo.increaseViewCount();
			tourInfoRepository.save(tourInfo);
		}
	}

	public TourInfoResponseDto getTourInfo(String contentId) {
		TourInfo tourInfo = findById(contentId);
		return TourInfoResponseDto.builder()
			.contentId(tourInfo.getContentId())
			.views(tourInfo.getViews())
			.description(tourInfo.getContentTypeId().getDescription())
			.build();
	}

	public List<TourInfoResponseDto> getTourInfoList() {
		return tourInfoRepository.findAll().stream()
			.map(tourInfo -> TourInfoResponseDto.builder()
				.contentId(tourInfo.getContentId())
				.views(tourInfo.getViews())
				.description(tourInfo.getContentTypeId().getDescription())
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

	private void saveContent(String contentId, String contentTypeId) {
		TourContentType type = TourContentType.fromCode(contentTypeId);
		TourInfo tourInfo = TourInfo.builder()
			.contentId(contentId)
			.views(1L)
			.contentTypeId(type)
			.build();
		tourInfoRepository.save(tourInfo);
	}

	private TourInfo findById(String contentId) {
		return tourInfoRepository.findById(contentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 관광 정보가 존재하지 않습니다."));
	}

	private TourInfo findById(String contentId, String type) {
		return tourInfoRepository.findById(contentId).orElse(null);
	}

	public List<TourInfoResponseDto> getTourInfoListByType(TourContentType contentId) {
		return tourInfoRepository.findAllByTypes(contentId).stream()
			.map(tourInfo -> TourInfoResponseDto.builder()
				.contentId(tourInfo.getContentId())
				.views(tourInfo.getViews())
				.description(tourInfo.getContentTypeId().getDescription())
				.build())
			.toList();
	}

	public List<TourInfoResponseDto> getTop10TourInfo() {
		List<TourInfoResponseDto> top10tourInfo = tourInfoRepository.top10tourInfo(PageRequest.of(0, 10)).stream()
			.map(tourInfo -> {
				return TourInfoResponseDto.builder()
					.contentId(tourInfo.getContentId())
					.views(tourInfo.getViews())
					.description(tourInfo.getContentTypeId().getDescription())
					.title(tourInfoOpenApiService.findByTourInfoDetail(tourInfo.getContentId()).get(0).title())
					.build();
			})
			.toList();
		return top10tourInfo;
	}
}
