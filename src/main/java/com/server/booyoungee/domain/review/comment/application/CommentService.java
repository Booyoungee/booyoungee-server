package com.server.booyoungee.domain.review.comment.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.comment.dto.request.CommentRequest;
import com.server.booyoungee.domain.review.comment.dto.response.CommentPersistResponse;
import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserService userService;
	private final PlaceService placeService;

	@Transactional
	public CommentPersistResponse saveReview(Long userId, CommentRequest request) {
		User user = userService.findByUser(userId);
		Place place = placeService.getByPlaceId(request.placeId());

		Comment comment = Comment.of(user, place, request.content(), request.stars());
		commentRepository.save(comment);
		return CommentPersistResponse.from(comment);
	}
}
