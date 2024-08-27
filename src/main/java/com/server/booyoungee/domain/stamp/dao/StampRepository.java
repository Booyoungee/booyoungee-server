package com.server.booyoungee.domain.stamp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {

	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stamp s WHERE s.user = :user AND s.place = :place")
	boolean existsByUserAndPlaceId(@Param("user") User user, @Param("place") Place place);

	@Query("SELECT s FROM Stamp s WHERE s.user = :user")
	List<Stamp> findAllByUser(@Param("user") User user);

	Optional<Stamp> findByUserAndStampId(User user, Long stampId);

	List<Stamp> findByPlaceId(Place place);

}
