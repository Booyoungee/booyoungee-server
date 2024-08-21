package com.server.booyoungee.domain.review.comment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPlaceId(Long placeId);
	List<Comment> findAllByWriter(User user);
}
