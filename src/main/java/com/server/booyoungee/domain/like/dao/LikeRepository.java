package com.server.booyoungee.domain.like.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.like.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
