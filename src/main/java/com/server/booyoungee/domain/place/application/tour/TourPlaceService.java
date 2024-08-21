package com.server.booyoungee.domain.place.application.tour;

import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.place.dao.tour.TourPlaceRepository;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourPlaceResponseDto;
import com.server.booyoungee.domain.place.exception.NotFoundPlaceException;
import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;
import com.server.booyoungee.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourPlaceService {

	private final TourPlaceRepository tourPlaceRepository;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@Transactional //북마크 조회시 사용
	public TourInfoDetailsResponseDto getTour(Long placeId) throws IOException {

		TourPlace tourPlace = tourPlaceRepository.findById(placeId)
			.orElseThrow(NotFoundPlaceException::new);
		TourInfoDetailsResponseDto tourInfo = getTourDetails(tourPlace.getContentId());
		tourPlaceRepository.save(tourPlace);

		return tourInfo;
	}

	public TourInfoDetailsResponseDto getTourDetails(String contentId) {
		List<TourInfoDetailsResponseDto> tourInfoList = tourInfoOpenApiService.getCommonInfoByContentId(contentId);
		if (tourInfoList.isEmpty())
			throw new CustomException(NOT_FOUND_TOUR_PLACE);
		TourInfoDetailsResponseDto tourInfo = tourInfoList.get(0);
		return tourInfo;
	}

	@Transactional
	public Place saveTourPlace(Long placeId) {
		TourPlace tourPlace = tourPlaceRepository.findByContentId(placeId.toString())
			.orElse(null);
		if (tourPlace == null) {
			tourPlace = TourPlace.of(placeId.toString(), getTourDetails(placeId.toString()).contenttypeid());

		}
		tourPlace.increaseViewCount();
		return tourPlaceRepository.save(tourPlace);
	}

	public List<TourPlaceResponseDto> getTourInfoListByType(TourContentType contentId) {

		String type = TourContentType.fromCode(contentId.getCode()).getDescription();
		List<TourPlaceResponseDto> tourInfoList = tourPlaceRepository.findByContentTypeId(type).stream()
			.map(tourInfo -> TourPlaceResponseDto.from(tourInfo))
			.collect(Collectors.toList());
		return tourInfoList;
	}

	public List<TourPlaceResponseDto> getTourInfoList() {
		List<TourPlaceResponseDto> tourInfoList = tourPlaceRepository.findAll().stream()
			.map(tourInfo -> TourPlaceResponseDto.from(tourInfo))
			.collect(Collectors.toList());
		return tourInfoList;
	}

	public List<TourPlace> getTop10TourInfo() {
		List<TourPlace> top10tourInfo = tourPlaceRepository.top10TourPlace(PageRequest.of(0, 10));
		return top10tourInfo;
	}

}
