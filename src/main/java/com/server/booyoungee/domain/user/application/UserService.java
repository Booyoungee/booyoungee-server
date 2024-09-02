package com.server.booyoungee.domain.user.application;

import com.server.booyoungee.domain.user.dto.response.UserPersistResponse;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.UserResponse;
import com.server.booyoungee.domain.user.exception.DuplicateNicknameException;
import com.server.booyoungee.domain.user.exception.NotFoundUserException;
import com.server.booyoungee.global.utils.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
			.orElseThrow(NotFoundUserException::new);
	}

	public String updateNickname(User user, String nickname) {
		String validatedNickname = duplicateNickname(nickname);
		user.updateName(validatedNickname);
		userRepository.save(user);
    return validatedNickname;
	}
	@Transactional
	public String duplicateNickname(String nickname) {
		if (userRepository.existsByName(nickname)) {
			throw new DuplicateNicknameException();
		} else {
			return nickname;
		}
	}

    public UserResponse getUser(User user) {
		return UserResponse.of(user.getUserId(), user.getName());
    }
	@Transactional
	public UserPersistResponse deleteUser(User user) {
		if (!userRepository.existsById(user.getUserId())) {
			throw new NotFoundUserException(); //404
		}
		UserPersistResponse response = UserPersistResponse.of(user.getUserId());
		userRepository.delete(user);
		return response;
	}

}
