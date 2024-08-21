package com.server.booyoungee.domain.review.comment.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.comment.dto.request.CommentRequest;
import com.server.booyoungee.domain.review.comment.dto.response.CommentListResponse;
import com.server.booyoungee.domain.review.comment.dto.response.CommentPersistResponse;
import com.server.booyoungee.domain.review.comment.exception.NotFoundCommentException;
import com.server.booyoungee.domain.review.comment.exception.UserNotWriterOfCommentException;
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

	public CommentListResponse getReviewList(Long placeId) {
		List<Comment> comments = commentRepository.findAllByPlaceId(placeId);
		return CommentListResponse.from(comments);
	}

	public CommentListResponse getMyReviewList(Long userId) {
		List<Comment> comments = commentRepository.findAllByWriterUserId(userId);
		return CommentListResponse.from(comments);
	}
	
	@Transactional
	public CommentPersistResponse deleteReview(Long userId, Long commentId) {
		User user = userService.findByUser(userId);
		Comment comment = getComment(commentId);
		validateReviewWriter(comment, user);
		commentRepository.deleteById(commentId);
		return CommentPersistResponse.from(comment);
	}

	private Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).
			orElseThrow(NotFoundCommentException::new);
	}

	private void validateReviewWriter(Comment comment, User user) {
		if (!comment.getWriter().equals(user)) {
			throw new UserNotWriterOfCommentException();
		}
	}
}
