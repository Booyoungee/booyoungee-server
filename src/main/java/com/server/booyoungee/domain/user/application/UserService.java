package com.server.booyoungee.domain.user.application;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.ErrorCode;
import com.server.booyoungee.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

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

	public void updateNickname(User user, String nickname) {
		try {
			// Check if the nickname already exists
			String validatedNickname = duplicateNickname(nickname);

			// Update the user's nickname
			user.updateName(validatedNickname);
			// Save the updated user entity to the repository
			userRepository.save(user);
		} catch (Exception e) {
			// Handle any other exceptions that might occur
			throw new CustomException(ErrorCode.DUPLICATE_ERROR);
		}
	}

	public String duplicateNickname(String nickname) {
		if (userRepository.existsByName(nickname)) {
			throw new CustomException(ErrorCode.DUPLICATE_ERROR);
		} else {
			return nickname;
		}
	}
}
