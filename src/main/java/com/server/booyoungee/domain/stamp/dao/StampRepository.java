package com.server.booyoungee.domain.stamp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.PlaceStampCountDto;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {

	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stamp s WHERE s.user = :user AND s.placeId = :placeId")
	boolean existsByUserAndPlaceId(@Param("user") User user, @Param("placeId") String placeId);

	@Query("SELECT s FROM Stamp s WHERE s.user = :user")
	List<Stamp> findAllByUser(@Param("user") User user);

	Optional<Stamp> findByUserAndStampId(User user, Long stampId);

	List<Stamp> findByPlaceId(String placeId);

	@Query("SELECT new com.server.booyoungee.domain.stamp.dto.PlaceStampCountDto(s.placeId, COUNT(s), s.type) " +
		"FROM Stamp s " +
		"GROUP BY s.placeId, s.type " +
		"ORDER BY COUNT(s) DESC")
	List<PlaceStampCountDto> findPlaceStampCounts();

	@Query("SELECT new com.server.booyoungee.domain.stamp.dto.PlaceStampCountDto(s.placeId, COUNT(s), s.type) " +
		"FROM Stamp s " +
		"GROUP BY s.placeId, s.type " +
		"ORDER BY COUNT(s) DESC")
	Page<PlaceStampCountDto> findPlaceStampCounts(Pageable pageable);

}
