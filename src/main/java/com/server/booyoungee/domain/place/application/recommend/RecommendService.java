package com.server.booyoungee.domain.place.application.recommend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.server.booyoungee.domain.user.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.dao.recommend.RecommendRepository;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.RecommendPlace;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.place.dto.response.recommend.RecommendPersistResponse;
import com.server.booyoungee.domain.place.dto.response.recommend.RecommendPlaceListResponse;
import com.server.booyoungee.domain.place.exception.DuplicatePlaceExcepiton;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {

	private final RecommendRepository recommendRepository;
	private final PlaceService placeService;

	@Transactional
	public RecommendPersistResponse addRecommend(Long placeId, PlaceType type) {

		if (recommendRepository.existsByPlaceId(placeId)) {
			throw new DuplicatePlaceExcepiton();
		}

		Place place = placeService.getByPlaceId(placeId, type.getKey());
		RecommendPlace recommendPlace = RecommendPlace.of(place, type.getKey(), true);
		RecommendPlace recmmend = recommendRepository.save(recommendPlace);

		return RecommendPersistResponse.of(recmmend.getId(), placeId);
	}

	@Transactional
	public RecommendPersistResponse deleteRecommend(Long id) {
		recommendRepository.deleteById(id);
		return RecommendPersistResponse.of(id, null);
	}

	@Transactional
	public List<RecommendPlace> updateRecommend() {
		//TODO 기존 데이터의 isRecommend를 false로 변경
		recommendRepository.updateAllToNotRecommendPlace();
		return recommendRepository.findAll();

	}

	@Transactional
	public RecommendPlaceListResponse getRecommendList(User user) throws IOException {
		Pageable topFive = PageRequest.of(0, 5);
		List<RecommendPlace> places = recommendRepository.findAll(topFive).getContent();

		List<PlaceDetailsResponse> placeList = new ArrayList<>();
		for (RecommendPlace place : places) {
			PlaceType type = PlaceType.valueOf(place.getType());
			PlaceDetailsResponse placeDetails = placeService.getDetails(place.getPlace().getId(),
				type,user);
			placeList.add(placeDetails);
		}
		RecommendPlaceListResponse response = RecommendPlaceListResponse.of(placeList);
		return response;
	}

}
