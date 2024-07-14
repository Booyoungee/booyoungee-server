package com.server.booyoungee.domain.login.application;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.login.domain.Constants;
import com.server.booyoungee.domain.login.domain.enums.Provider;
import com.server.booyoungee.domain.login.dto.SocialInfoDto;
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.ErrorCode;
import com.server.booyoungee.global.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final KakaoLoginService kakaoLoginService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@Transactional
	public JwtTokenResponse login(String providerToken, LoginRequestDto request) throws IOException {
		SocialInfoDto socialInfo = getSocialInfo(request, providerToken);
		User user = loadOrCreateUser(request.provider(), socialInfo);
		return generateTokensWithUpdateRefreshToken(user);
	}

	private SocialInfoDto getSocialInfo(LoginRequestDto request, String providerToken) {
		if (request.provider().toString().equals(Provider.KAKAO.toString())) {
			return kakaoLoginService.getInfo(providerToken);
		} else {
			throw new CustomException(ErrorCode.NULL_POINT_ERROR);
		}
	}

	private User loadOrCreateUser(Provider provider, SocialInfoDto socialInfo) throws IOException {
		boolean isRegistered = userRepository.existsBySerialId(socialInfo.serialId());

		if (!isRegistered) {
			User newUser = User.builder()
				.serialId(socialInfo.serialId())
				.email(socialInfo.email())
				.name(socialInfo.name())
				.role(User.Role.USER)
				.build();
			userRepository.save(newUser);
		}

		return userRepository.findBySerialId(socialInfo.serialId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
	}

	private JwtTokenResponse generateTokensWithUpdateRefreshToken(User user) {
		JwtTokenResponse jwtTokenResponse = jwtUtil.generateTokens(user.getUserId(), user.getRole());
		user.updateRefreshToken(jwtTokenResponse.refreshToken());
		return jwtTokenResponse;
	}

	@Transactional
	public JwtTokenResponse refresh(String token) {
		String refreshToken = getToken(token);
		Claims claims = jwtUtil.getTokenBody(refreshToken);
		if (claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class) != null) {
			throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);
		}
		Long userId = claims.get(Constants.USER_ID_CLAIM_NAME, Long.class);
		User user = userRepository.findByUserId(userId).get();
		if (!user.getRefreshToken().equals(refreshToken)) {
			throw new CustomException(ErrorCode.INVALID_JWT);
		}
		return generateTokensWithUpdateRefreshToken(user);
	}

	private String getToken(String token) {
		if (token.startsWith(Constants.BEARER_PREFIX)) {
			return token.substring(Constants.BEARER_PREFIX.length());
		} else {
			return token;
		}
	}

	@Transactional
	public void logout(Long userId) {
		User user = userRepository.findByUserId(userId).get();
		user.updateRefreshToken(null);
	}

}