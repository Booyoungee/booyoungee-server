package com.server.booyoungee.domain.user.application;

import java.util.Date;

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

	public Date getTokenExpiredTime(String token) {
		return jwtUtil.getExpirationDateFromToken(token);
	}

	public void updateNickname(String nickname) {
	}

	public String duplicateNickname(String nickname) {
		if (userRepository.existsByName(nickname)) {
			// Throw a custom exception if the nickname is already in use
			throw new CustomException(ErrorCode.DUPLICATE_ERROR);
		} else {
			// Return the nickname if it is not a duplicate
			return nickname;
		}
	}
}
