package com.server.booyoungee.domain.like.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.like.dao.LikeRepository;
import com.server.booyoungee.domain.like.domain.Like;
import com.server.booyoungee.domain.like.dto.request.LikeRequest;
import com.server.booyoungee.domain.like.dto.response.LikePersistResponse;
import com.server.booyoungee.domain.like.exception.NotFoundLikeException;
import com.server.booyoungee.domain.like.exception.UserNotLikeOwnerException;
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

	@Transactional
	public LikePersistResponse deleteLike(User user, Long placeId) {
		Place place = placeService.getByPlaceId(placeId);
		Like like = likeRepository.findByUserAndPlace(user, place)
			.orElseThrow(NotFoundLikeException::new);
		validateIsOwner(like, user);
		likeRepository.delete(like);
		return LikePersistResponse.from(like);
	}

	public List<Long> getTopPlacesByLikes() {
		return likeRepository.findTopPlacesByLikes();
	}

	public int getLikeCountByPlaceId(Long placeId) {
		return likeRepository.countByPlaceId(placeId);
	}

	private Like getLikeById(Long likeId) {
		return likeRepository.findById(likeId)
			.orElseThrow((NotFoundLikeException::new));
	}

	private void validateIsOwner(Like like, User user) {
		if (!like.getUser().getUserId().equals(user.getUserId())) {
			throw new UserNotLikeOwnerException();
		}
	}
}
