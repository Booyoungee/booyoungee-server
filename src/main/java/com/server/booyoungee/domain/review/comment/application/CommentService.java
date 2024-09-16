package com.server.booyoungee.domain.review.comment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.comment.dto.request.CommentRequest;
import com.server.booyoungee.domain.review.comment.dto.response.CommentListResponse;
import com.server.booyoungee.domain.review.comment.dto.response.CommentPersistResponse;
import com.server.booyoungee.domain.review.comment.exception.NotFoundCommentException;
import com.server.booyoungee.domain.review.comment.exception.UserNotWriterOfCommentException;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.common.PlaceType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PlaceService placeService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@Transactional
	public CommentPersistResponse saveReview(User user, CommentRequest request) {
		Place place = placeService.getByPlaceId(request.placeId(), request.type().getKey());
		Comment comment = Comment.of(user, place, request.content(), request.stars(), request.type());
		commentRepository.save(comment);
		return CommentPersistResponse.from(comment);
	}

	@Transactional
	public CommentPersistResponse accuseReview(User user, Long commentId) {
		Comment comment = getComment(commentId);
		comment.accuseReview(user);
		return CommentPersistResponse.from(comment);
	}

	@Transactional
	public CommentListResponse getReviewList(Long placeId) {
		List<Comment> comments = commentRepository.findAllByPlaceId(placeId);
		return CommentListResponse.from(comments);
	}

	@Transactional
	public CommentListResponse getReviewList(Long placeId, User user) {
		List<User> blockedUsers = user.getBlockedUsers();

		// If the user hasn't blocked anyone, simply return all comments
		if (blockedUsers == null || blockedUsers.isEmpty()) {
			List<Comment> comments = commentRepository.findAllByPlaceId(placeId);
			return CommentListResponse.from(comments);
		}
		// Fetch comments that are not from blocked users
		List<Comment> comments = commentRepository.findAllByPlaceIdAndWriterNotIn(placeId, blockedUsers);

		return CommentListResponse.from(comments);
	}

	public List<Stars> getStars(Long placeId) {
		List<Comment> comments = commentRepository.findAllByPlaceId(placeId);
		return comments.stream()
			.map(Comment::getStars)
			.collect(Collectors.toList());
	}

	@Transactional
	public CommentListResponse getMyReviewList(User user) {
		List<Comment> comments = commentRepository.findAllByWriter(user);

		List<String> places = comments.stream()
			.map(this::resolvePlaceName)
			.collect(Collectors.toList());

		return CommentListResponse.from(comments, places);
	}

	private String resolvePlaceName(Comment comment) {
		final String TOUR_TYPE_UPPER = "TOUR";
		final String TOUR_TYPE_LOWER = "tour";

		if (comment.getType().equals(TOUR_TYPE_UPPER) || comment.getType().equals(TOUR_TYPE_LOWER)) {
			return tourInfoOpenApiService
				.getCommonInfoByContentId(String.valueOf(comment.getPlace().getId()))
				.get(0).title();
		} else {
			return comment.getPlace().getName();
		}
	}


	@Transactional
	public CommentPersistResponse deleteReview(User user, Long commentId) {
		Comment comment = getComment(commentId);
		validateReviewWriter(comment, user);
		commentRepository.deleteById(commentId);
		return CommentPersistResponse.from(comment);
	}

	public int getReviewCountByPlaceId(Long placeId) {
		return commentRepository.countByPlaceId(placeId);
	}

	public List<Long> getTopPlacesByReviews() {
		return commentRepository.findTopPlacesByReviews();
	}

	public List<Long> getTopPlacesByStars() {
		return commentRepository.findTopPlacesByStars();
	}

	private Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).
			orElseThrow(NotFoundCommentException::new);
	}

	private void validateReviewWriter(Comment comment, User user) {
		if (!comment.getWriter().getUserId().equals(user.getUserId())) {
			throw new UserNotWriterOfCommentException();
		}
	}
}
