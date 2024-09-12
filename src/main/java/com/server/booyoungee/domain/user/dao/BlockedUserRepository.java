package com.server.booyoungee.domain.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.booyoungee.domain.user.domain.BlockUser;
import com.server.booyoungee.domain.user.domain.User;

public interface BlockedUserRepository extends JpaRepository<BlockUser, Long> {

	boolean existsByBlockerAndBlocked(User currentUser, User blockedUser);

	BlockUser findByBlockerAndBlocked(User currentUser, User blockedUser);
}
