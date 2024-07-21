package com.server.booyoungee.domain.store.application;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;
import com.server.booyoungee.domain.store.dao.StoreRepository;
import com.server.booyoungee.domain.store.domain.Store;
import com.server.booyoungee.domain.store.dto.StoreResponseDto;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.ErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final StoreRepository storeRepository;
	private final PlaceSearchService placeSearchService;

	public List<StoreResponseDto> getStoreByName(String name) {

		List<Store> stores = storeRepository.findAllByBusinessName(name);
		return stores.stream()
			.map(StoreResponseDto::new)
			.collect(Collectors.toList());
	}

	public List<StoreResponseDto> getStoreByDistrict(String district) {

		List<Store> stores = storeRepository.findAllByDistrict(district);
		return stores.stream()
			.map(StoreResponseDto::new)
			.collect(Collectors.toList());
	}

	public List<StoreResponseDto> getStores(Pageable pageable) {
		List<Store> stores = storeRepository.findAllOrderByViews(pageable).getContent();
		return stores.stream()
			.map(StoreResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public Object getStoreById(Long storeId) throws IOException {
		Store store = storeRepository.findByStoreId(storeId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
		store.setViews(store.getViews() + 1);
		storeRepository.save(store);
		return placeSearchService.searchByKeywordDetailsAddtypeOption(store.getBusinessName(), "store");
	}

	public void restoreViews() {
		storeRepository.resetViewsForAllStores();
	}
}
