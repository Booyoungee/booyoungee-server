package com.server.booyoungee.domain.like.dao;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.like.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPlace(User user, Place place);
}
