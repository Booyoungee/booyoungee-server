package com.server.booyoungee.domain.place.application.store;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;
import com.server.booyoungee.domain.place.dao.store.StorePlaceRepository;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;
import com.server.booyoungee.domain.place.dto.response.store.StoreResponseDto;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.ErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorePlaceService {
	private final StorePlaceRepository storePlaceRepository;
	private final PlaceSearchService placeSearchService;

	public List<StoreResponseDto> getStoreByName(String name) {

		List<StorePlace> stores = storePlaceRepository.findAllByBusinessName(name);
		return stores.stream()
			.map(StoreResponseDto::new)
			.collect(Collectors.toList());
	}

	public List<StoreResponseDto> getStoreByDistrict(String district) {

		List<StorePlace> stores = storePlaceRepository.findAllByDistrict(district);
		return stores.stream()
			.map(StoreResponseDto::new)
			.collect(Collectors.toList());
	}

	public List<StoreResponseDto> getStores(Pageable pageable) {
		List<StorePlace> stores = storePlaceRepository.findAllOrderByViews(pageable).getContent();
		return stores.stream()
			.map(StoreResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public Object getStoreById(Long storeId) throws IOException {
		StorePlace store = storePlaceRepository.findByStoreId(storeId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
		// store.setViewCount(store.getViewCount() + 1); TODO: 도메인 규칙에 viewCount 증가 추가
		storePlaceRepository.save(store);
		return placeSearchService.searchByKeywordDetailsAddtypeOption(store.getName(), "store");
	}

	public void restoreViews() {
		storePlaceRepository.resetViewsForAllStores();
	}
}
