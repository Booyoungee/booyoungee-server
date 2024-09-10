package com.server.booyoungee.domain.like.dao;

import java.util.List;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.like.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPlace(User user, Place place);

    int countByPlaceId(Long placeId);

    @Query("SELECT l.place.id FROM Like l GROUP BY l.place.id ORDER BY COUNT(l.place.id) DESC")
    List<Long> findTopPlacesByLikes();

}
