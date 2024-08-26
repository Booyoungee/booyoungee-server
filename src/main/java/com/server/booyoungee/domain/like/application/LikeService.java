package com.server.booyoungee.domain.like.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.like.dao.LikeRepository;
import com.server.booyoungee.domain.like.domain.Like;
import com.server.booyoungee.domain.like.dto.request.LikeRequest;
import com.server.booyoungee.domain.like.dto.response.LikePersistResponse;
import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final LikeRepository likeRepository;
	private final PlaceService placeService;

	@Transactional
	public LikePersistResponse saveLike(User user, LikeRequest request) {
		Place place = placeService.getByPlaceId(request.placeId());
		Like like = Like.of(user, place);
		likeRepository.save(like);
		return LikePersistResponse.from(like);
	}
}
