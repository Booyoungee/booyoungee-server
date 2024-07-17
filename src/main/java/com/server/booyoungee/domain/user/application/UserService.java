package com.server.booyoungee.domain.user.application;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void createTestUser() {
		try {
			User testUser = User.builder()
				.name("test")
				.email("test@mail.com")
				.role(User.Role.USER)
				.serialId("testID")
				.refreshToken("testToken")
				.build();
			userRepository.save(testUser);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findByUser(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
	}
}
