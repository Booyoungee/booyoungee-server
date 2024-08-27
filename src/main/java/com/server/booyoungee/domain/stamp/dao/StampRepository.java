package com.server.booyoungee.domain.stamp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {

	boolean existsByUserAndPlace(User user, Place place);

	List<Stamp> findAllByUser(User user);

	Optional<Stamp> findByUserAndStampId(User user, Long stampId);

}
