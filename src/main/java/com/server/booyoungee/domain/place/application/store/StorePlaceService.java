package com.server.booyoungee.domain.place.application.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.kakaoMap.application.KakaoAddressSearchService;
import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoAddressToCode;
import com.server.booyoungee.domain.kakaoMap.dto.response.SearchDetailDto;
import com.server.booyoungee.domain.like.dao.LikeRepository;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.dao.store.StorePlaceRepository;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.store.StorePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryListResponse;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.global.common.PageableResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorePlaceService {
	private final StorePlaceRepository storePlaceRepository;
	private final PlaceSearchService placeSearchService;
	private final KakaoAddressSearchService kakaoAddressSearchService;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	public StorePlaceListResponse getStoreByName(String name) {
		List<StorePlace> stores = storePlaceRepository.findAllByName(name);
		return StorePlaceListResponse.of(stores);
	}

	public StorePlaceListResponse getStoreByDistrict(String district) {
		List<StorePlace> stores = storePlaceRepository.findAllByDistrict(district);
		return StorePlaceListResponse.of(stores);
	}

	public StorePlacePageResponse<StorePlace> getStores(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Long totalElements = storePlaceRepository.count();
		List<StorePlace> stores = storePlaceRepository.findAllOrderByViewCount(pageable);
		List<StorePlaceResponse> storePlaceResponses = stores.stream()
			.map(StorePlaceResponse::from)
			.collect(Collectors.toList());
		return StorePlacePageResponse.of(storePlaceResponses, PageableResponse.of(pageable, totalElements));
	}

	@Transactional
	public SearchDetailDto getStoreById(Long storeId) throws IOException {
		StorePlace store = storePlaceRepository.findByStoreId(storeId)
			.orElseThrow(NotFoundStorePlaceException::new);
		store.increaseViewCount();
		storePlaceRepository.save(store);
		return placeSearchService.searchByKeywordDetailsAddtypeOption(store.getName(), "store");
	}

	public StorePlaceResponse getStore(Long storeId) throws IOException {
		StorePlace store = storePlaceRepository.findById(storeId)
			.orElseThrow(NotFoundStorePlaceException::new);
		store.increaseViewCount();
		storePlaceRepository.save(store);
		return StorePlaceResponse.from(store);
	}

	@Transactional
	public PlaceSummaryListResponse getStorePlacesByFilter(String filter) {
		List<Long> storePlaceIds = new ArrayList<>();
		List<PlaceSummaryResponse> storePlaces = new ArrayList<>();
		PlaceType type = PlaceType.store;
		storePlaceIds = switch (filter) {
			case "like" -> likeRepository.findTopPlacesByLikes();
			case "review" -> commentRepository.findTopPlacesByReviews();
			case "star" -> commentRepository.findTopPlacesByStars();
			default -> storePlaceIds;
		};

		storePlaceIds.forEach(id -> {
			Optional<StorePlace> storePlace = storePlaceRepository.findById(id);
			if (storePlace.isPresent()) {
				List<Stars> stars = commentRepository.findAllByPlaceId(id)
					.stream()
					.map(Comment::getStars)
					.collect(Collectors.toList());
				int likeCount = likeRepository.countByPlaceId(id);
				int reviewCount = commentRepository.countByPlaceId(id);
				TourInfoDetailsResponseDto tourInfo = null;
				List<TourInfoDetailsResponseDto> tourInfoList = tourInfoOpenApiService.getTourInfoByKeyword(
					storePlace.get().getName());
				List<String> image = new ArrayList<>();
				// Use Optional to handle potential null or empty list
				tourInfo = tourInfoList != null && !tourInfoList.isEmpty() ? tourInfoList.get(0) : null;
				if (tourInfo != null) {
					image.add(tourInfo.firstimage());
				}

				storePlaces.add(
					PlaceSummaryResponse.of(storePlace.get(), stars, likeCount, reviewCount, type, image, storePlace.get().getMapX(), storePlace.get().getMapY())
				);
			}
		});
		return PlaceSummaryListResponse.of(storePlaces);
	}

	public void restoreViews() {
		storePlaceRepository.resetViewsForAllStores();
	}

	private List<StorePlaceResponse> convertToStorePlaceResponse(List<StorePlace> stores) {
		return stores.stream()
			.map(StorePlaceResponse::from)
			.collect(Collectors.toList());
	}

	public void updateXY() {
		List<StorePlace> list = storePlaceRepository.findAll();
		for (StorePlace store : list) {
			KakaoAddressToCode dto = kakaoAddressSearchService.searchAddressXY(store.getName(), 1, 1);
			String x = dto.getDocuments().get(0).getX();
			String y = dto.getDocuments().get(0).getY();

			store.updateMap(x, y);
			storePlaceRepository.save(store);
			System.out.println("update: " + store.getMapX() + "," + store.getMapY());
		}
	}

	public List<StorePlace> Top10StorePlaces() {
		return storePlaceRepository.top10StorePlace(PageRequest.of(0, 10));
	}

	public StorePlaceListResponse getStorePlacesNearby(String mapX, String mapY, int radius) {
		List<StorePlace> storePlaces = storePlaceRepository.findStorePlacesByMapXAndMapY(mapX, mapY, radius);
		return StorePlaceListResponse.of(storePlaces);
	}
}
