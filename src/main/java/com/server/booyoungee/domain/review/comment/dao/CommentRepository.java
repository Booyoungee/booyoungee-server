package com.server.booyoungee.domain.review.comment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	int countByPlaceId(Long placeId);

	@Query("SELECT c.place.id FROM Comment c GROUP BY c.place.id ORDER BY COUNT(c.id) DESC")
	List<Long> findTopPlacesByReviews();

	@Query("SELECT c.place.id FROM Comment c GROUP BY c.place.id ORDER BY AVG(c.stars.stars) DESC")
	List<Long> findTopPlacesByStars();

	List<Comment> findAllByPlaceId(Long placeId);

	List<Comment> findAllByWriter(User user);

	@Query("SELECT c FROM Comment c WHERE c.place.id = :placeId AND c.writer NOT IN :blockedUsers")
	List<Comment> findAllByPlaceIdAndWriterNotIn(@Param("placeId") Long placeId,
		@Param("blockedUsers") List<User> blockedUsers);
}
