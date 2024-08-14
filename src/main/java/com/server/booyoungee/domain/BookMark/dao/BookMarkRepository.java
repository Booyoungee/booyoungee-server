package com.server.booyoungee.domain.BookMark.dao;

import java.util.List;

import com.server.booyoungee.domain.place.domain.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.booyoungee.domain.BookMark.domain.BookMark;
import com.server.booyoungee.domain.user.domain.User;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

	@Query("SELECT bm FROM BookMark bm WHERE bm.userId = :user")
	List<BookMark> findAllByUser(@Param("user") User user);


	boolean existsByUserIdAndPlaceId(User userId, Long placeId);

	BookMark findByBookMarkIdAndUserId(Long BookMarkId, User userId);

	boolean existsByUserIdAndPlaceIdAndType(User user, Long placeId, PlaceType type);
}

