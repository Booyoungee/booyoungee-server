package com.server.booyoungee.domain.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(Long userId);

	boolean existsBySerialId(String s);

	Optional<User> findBySerialId(String s);

	boolean existsByName(String name);
}
