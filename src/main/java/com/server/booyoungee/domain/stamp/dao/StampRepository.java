package com.server.booyoungee.domain.stamp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {

	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stamp s WHERE s.user = :user AND s.placeId = :placeId")
	boolean existsByUserAndPlaceId(@Param("user") User user, @Param("placeId") String placeId);

}
