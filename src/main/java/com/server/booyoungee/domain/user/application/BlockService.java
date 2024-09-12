package com.server.booyoungee.domain.user.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.user.dao.BlockedUserRepository;
import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.BlockUser;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.UserPersistResponse;
import com.server.booyoungee.domain.user.exception.DuplicateBlockUserException;
import com.server.booyoungee.domain.user.exception.NotFoundUserException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockService {
	private final UserRepository userRepository;
	private final BlockedUserRepository blockedUserRepository;

	@Transactional
	public UserPersistResponse blockUser(User currentUser, Long blockedUserId) {
		Optional<User> blockedUser = userRepository.findById(blockedUserId);
		if (blockedUser.isEmpty()) {
			throw new NotFoundUserException();
		}

		// Check if user is already blocked
		boolean alreadyBlocked = blockedUserRepository.existsByBlockerAndBlocked(currentUser, blockedUser.get());
		if (alreadyBlocked) {
			throw new DuplicateBlockUserException();
		}

		BlockUser blocked = BlockUser.of(currentUser, blockedUser.get());
		blockedUserRepository.save(blocked);
		return UserPersistResponse.of(blocked.getBlocked().getUserId());
	}

	@Transactional
	public UserPersistResponse unblockUser(User currentUser, Long blockedUserId) {
		Optional<User> blockedUser = userRepository.findById(blockedUserId);
		if (blockedUser.isEmpty()) {
			throw new NotFoundUserException();
		}

		BlockUser blocked = blockedUserRepository.findByBlockerAndBlocked(currentUser, blockedUser.get());
		if (blocked == null) {
			throw new NotFoundUserException();
		}

		blockedUserRepository.delete(blocked);
		return UserPersistResponse.of(blocked.getBlocked().getUserId());
	}
}
