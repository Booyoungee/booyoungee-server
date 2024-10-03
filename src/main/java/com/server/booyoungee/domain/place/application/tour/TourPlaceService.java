package com.server.booyoungee.domain.place.application.tour;

import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.like.dao.LikeRepository;
import com.server.booyoungee.domain.place.dao.tour.TourPlaceRepository;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.tour.TourContentType;
import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryListResponse;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryPageResponse;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoCommonResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourPlaceResponseDto;
import com.server.booyoungee.domain.place.exception.NotFoundPlaceException;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.global.common.PageableResponse;
import com.server.booyoungee.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourPlaceService {

	private final TourPlaceRepository tourPlaceRepository;
	private final TourInfoOpenApiService tourInfoOpenApiService;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public PlaceSummaryListResponse getTourPlacesByFilter(String filter) {
		List<Long> tourPlaceIds = new ArrayList<>();
		List<PlaceSummaryResponse> tourPlaces = new ArrayList<>();
		PlaceType type = PlaceType.tour;
		tourPlaceIds = switch (filter) {
			case "like" -> likeRepository.findTopPlacesByLikes();
			case "review" -> commentRepository.findTopPlacesByReviews();
			case "star" -> commentRepository.findTopPlacesByStars();
			default -> tourPlaceIds;
		};

		for (Long id : tourPlaceIds) {
			if (tourPlaces.size() >= 15) break;

			Optional<TourPlace> tourPlace = tourPlaceRepository.findById(id);
			if (tourPlace.isPresent()) {
				List<TourInfoDetailsResponseDto> response = tourInfoOpenApiService.getCommonInfoByContentId(
					tourPlace.get().getContentId());
				List<Stars> stars = commentRepository.findAllByPlaceId(id)
					.stream()
					.map(Comment::getStars)
					.collect(Collectors.toList());
				int likeCount = likeRepository.countByPlaceId(id);
				int reviewCount = commentRepository.countByPlaceId(id);
				List<String> image = new ArrayList<>();

				if (!response.isEmpty()) {
					image.add(response.get(0).firstimage());
				}

				tourPlaces.add(
					PlaceSummaryResponse.of(tourPlace.get(), response.get(0).title(), response.get(0).addr1(), stars,
						likeCount, reviewCount, type, image, response.get(0).mapx(), response.get(0).mapy()
					));
			}
		}

		return PlaceSummaryListResponse.of(tourPlaces);
	}

	@Transactional
	public PlaceSummaryPageResponse getPlaceByKeyword(List<TourInfoCommonResponse> dto, PageRequest request) {
		List<PlaceSummaryResponse> tourPlaces = new ArrayList<>();
		for (TourInfoCommonResponse response : dto) {
			Optional<TourPlace> tourPlace = tourPlaceRepository.findByContentId(response.contentid());
			if (tourPlace.isPresent()) {
				Long id = tourPlace.get().getId();
				List<Stars> stars = commentRepository.findAllByPlaceId(id)
					.stream()
					.map(Comment::getStars)
					.collect(Collectors.toList());
				int likeCount = likeRepository.countByPlaceId(id);
				int reviewCount = commentRepository.countByPlaceId(id);
				List<String> image = new ArrayList<>();
				image.add(response.firstimage());
				tourPlaces.add(
					PlaceSummaryResponse.of(tourPlace.get(), response.title(), response.addr1(), stars,
						likeCount, reviewCount, PlaceType.tour, image, response.mapx(), response.mapy()
					));
			}
		}
		return PlaceSummaryPageResponse.of(tourPlaces, PageableResponse.of(request));
	}

	@Transactional //북마크 조회시 사용
	public TourInfoDetailsResponseDto getTour(Long placeId) {

		TourPlace tourPlace = tourPlaceRepository.findById(placeId)
			.orElseThrow(NotFoundPlaceException::new);
		tourPlace.increaseViewCount();
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

	public void viewContents(List<TourInfoCommonResponse> jsonResult) {
		for (TourInfoCommonResponse tourInfoCommonResponse : jsonResult) {
			String contentId = tourInfoCommonResponse.contentid();
			String contentTypeId = tourInfoCommonResponse.contenttypeid();
			if (!isExist(contentId)) {
				saveContent(contentId, contentTypeId);
			}
		}
	}

	public void viewContent(String contentId, String contentTypeId) {
		if (!isExist(contentId)) {
			saveContent(contentId, contentTypeId);
		}
	}

	private void saveContent(String contentId, String contentTypeId) {
		TourPlace tourPlace = TourPlace.of(contentId, contentTypeId);
		tourPlaceRepository.save(tourPlace);
	}

	private boolean isExist(String contentId) {
		return tourPlaceRepository.existsByContentId(contentId);
	}

	public String getContentId(Long placeId) {
		TourPlace tourPlace = tourPlaceRepository.findById(placeId)
				.orElseThrow(NotFoundPlaceException::new);
		System.out.println("tourPlace.getContentId() = " + tourPlace.getContentId());
		return tourPlace.getContentId();
	}
}
