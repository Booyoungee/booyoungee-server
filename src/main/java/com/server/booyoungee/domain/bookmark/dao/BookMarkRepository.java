package com.server.booyoungee.domain.bookmark.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.booyoungee.domain.bookmark.domain.BookMark;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.user.domain.User;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

	@Query("SELECT bm FROM BookMark bm WHERE bm.userId = :user")
	List<BookMark> findAllByUser(@Param("user") User user);

	boolean existsByUserIdAndPlaceId(User userId, Place placeId);

	BookMark findByBookMarkIdAndUserId(Long BookMarkId, User userId);

	boolean existsByUserIdAndPlaceIdAndType(User user, Place placeId, PlaceType type);

	BookMark findByPlaceIdAndUserId(Place place, User user);
}

