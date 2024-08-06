package com.server.booyoungee.domain.place.application.store;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;
import com.server.booyoungee.domain.kakaoMap.dto.response.SearchDetailDto;
import com.server.booyoungee.domain.place.dao.store.StorePlaceRepository;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;
import com.server.booyoungee.global.common.PageableResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorePlaceService {
	private final StorePlaceRepository storePlaceRepository;
	private final PlaceSearchService placeSearchService;

	public StorePlaceListResponse getStoreByName(String name) {
		List<StorePlace> stores = storePlaceRepository.findAllByName(name);
		List<StorePlaceResponse> storePlaceResponses = convertToStorePlaceResponse(stores);
		return StorePlaceListResponse.of(storePlaceResponses);
	}

	public StorePlaceListResponse getStoreByDistrict(String district) {
		List<StorePlace> stores = storePlaceRepository.findAllByDistrict(district);
		List<StorePlaceResponse> storePlaceResponses = convertToStorePlaceResponse(stores);
		return StorePlaceListResponse.of(storePlaceResponses);
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
		StorePlace store = storePlaceRepository.findByStoreId(storeId)
			.orElseThrow(NotFoundStorePlaceException::new);
		store.increaseViewCount();
		storePlaceRepository.save(store);
		return StorePlaceResponse.from(store);
	}

	public void restoreViews() {
		storePlaceRepository.resetViewsForAllStores();
	}

	private List<StorePlaceResponse> convertToStorePlaceResponse(List<StorePlace> stores) {
		return stores.stream()
			.map(StorePlaceResponse::from)
			.collect(Collectors.toList());
	}
}
