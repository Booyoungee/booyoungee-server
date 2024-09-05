package com.server.booyoungee.domain.login.application;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.server.booyoungee.domain.login.domain.Constants;
import com.server.booyoungee.domain.login.domain.enums.Provider;
import com.server.booyoungee.domain.login.dto.SocialInfoDto;
import com.server.booyoungee.domain.login.dto.request.KakaoLoginRequest;
import com.server.booyoungee.domain.login.dto.request.LoginRequest;
import com.server.booyoungee.domain.login.dto.request.SignUpRequest;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.login.exception.ConflictUserException;
import com.server.booyoungee.domain.login.exception.NotFoundUserException;
import com.server.booyoungee.domain.login.exception.NotFoundUserInfoException;
import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.UserPersistResponse;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;
import com.server.booyoungee.global.utils.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final KakaoLoginService kakaoLoginService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final RestTemplate restTemplate;

	@Transactional
	public JwtTokenResponse login(KakaoLoginRequest providerToken, LoginRequest request) throws IOException {
		SocialInfoDto socialInfo = getSocialInfo(request, providerToken.accessToken());
		User user = loadOrCreateUser(socialInfo);
		String refreshToken = providerToken.refreshToken();
		if (refreshToken == null) {
			refreshToken = user.getRefreshToken();
		}
		return generateTokensWithUpdateRefreshToken(user, providerToken.accessToken(), refreshToken);
	}

	private SocialInfoDto getSocialInfo(LoginRequest request, String providerToken) {
		if (request.provider().toString().equals(Provider.KAKAO.toString())) {
			return kakaoLoginService.getInfo(providerToken);
		} else {
			throw new NotFoundUserInfoException(); //엑세스 토큰이 잘못됨
		}
	}

	private User loadOrCreateUser(SocialInfoDto socialInfo) {
		return userRepository.findBySerialId(socialInfo.serialId())
			.orElseGet(() -> {
				throw new NotFoundUserException(); //회원가입 필요 404
			});
	}

	@Transactional
	public JwtTokenResponse signup(SignUpRequest providerToken, String name, LoginRequest request) {
		SocialInfoDto socialInfo = getSocialInfo(request, providerToken.accessToken());
		User user = createUser(providerToken, name, socialInfo);
		return generateTokensWithUpdateRefreshToken(user, providerToken.accessToken(), user.getRefreshToken());
	}

	private User createUser(SignUpRequest providerToken, String name, SocialInfoDto socialInfo) {
		Optional<User> existingUser = userRepository.findBySerialId(socialInfo.serialId());
		if (existingUser.isPresent()) {
			throw new ConflictUserException(); // Assuming USER_ALREADY_EXISTS is a defined error code
		}

		User newUser = User.builder()
			.serialId(socialInfo.serialId())
			.email("")
			.name(name)
			.role(User.Role.USER)
			.refreshToken(providerToken.refreshToken()) // Initialize refreshToken as an empty string
			.build();
		userRepository.save(newUser);
		return newUser;
	}

	private JwtTokenResponse generateTokensWithUpdateRefreshToken(User user, String accessToken, String refreshToken) {
		JwtTokenResponse jwtTokenResponse = jwtUtil.generateTokens(user.getUserId(), user.getRole(), accessToken,
			refreshToken);
		user.updateRefreshToken(refreshToken);
		return jwtTokenResponse;
	}

	private String getToken(String token) {
		if (token.startsWith(Constants.BEARER_PREFIX)) {
			return token.substring(Constants.BEARER_PREFIX.length());
		} else {
			return token;
		}
	}

	@Transactional
	public UserPersistResponse logout(UserAuthentication authentication) {
		String accessToken = jwtUtil.getOriginalAccessToken(authentication.getAccessToken());
		String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, String.class);
		try {
			Long userId = Long.parseLong(authentication.getName());

			if (userId == null)
				throw new NotFoundUserInfoException();

			User user = userRepository.findByUserId(userId).get();

			if (user == null)
				throw new NotFoundUserException();

			user.updateRefreshToken("");
			return UserPersistResponse.of(userId);
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("HTTP error while getting access token from Kakao: " + e.getStatusCode() + " - "
				+ e.getResponseBodyAsString(), e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error while getting access token from Kakao", e);
		}

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