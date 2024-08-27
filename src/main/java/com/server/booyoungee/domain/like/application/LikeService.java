package com.server.booyoungee.domain.like.application;

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
	public LikePersistResponse deleteLike(User user, Long likeId) {
		Like like = getLikeById(likeId);
		validateIsOwner(like, user);
		likeRepository.delete(like);
		return LikePersistResponse.from(like);
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
