package com.server.booyoungee.domain.store.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.store.dao.StoreRepository;
import com.server.booyoungee.domain.store.domain.Store;
import com.server.booyoungee.domain.store.dto.StoreResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final StoreRepository storeRepository;

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
}
