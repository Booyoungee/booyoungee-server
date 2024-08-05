package com.server.booyoungee.global.utils;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.server.booyoungee.domain.login.domain.Constants;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.GlobalExceptionCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil implements InitializingBean {
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.access-token-expire-period}")
	private Integer accessTokenExpirePeriod;
	@Value("${jwt.refresh-token-expire-period}")
	@Getter
	private Integer refreshTokenExpirePeriod;

	private Key key;

	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes); // HS512용 보안 키 생성
	}

	public JwtTokenResponse generateTokens(Long id, User.Role role, String access, String refresh) {
		return JwtTokenResponse.of(
			generateToken(id, role, accessTokenExpirePeriod, access),
			refresh);
		//generateToken(id, null, refreshTokenExpirePeriod));
	}

	public Claims getTokenBody(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (MalformedJwtException ex) {
			throw new CustomException(GlobalExceptionCode.INVALID_JWT);
		} catch (ExpiredJwtException ex) {
			throw new CustomException(GlobalExceptionCode.EXPIRED_JWT);
		} catch (UnsupportedJwtException ex) {
			throw new CustomException(GlobalExceptionCode.UNSUPPORTED_JWT);
		} catch (IllegalArgumentException ex) {
			throw new CustomException(GlobalExceptionCode.JWT_IS_EMPTY);
		}
	}

	private String generateToken(Long id, User.Role role, Integer expirePeriod, String accessToken) {
		Claims claims = Jwts.claims();
		claims.put(Constants.USER_ID_CLAIM_NAME, id);
		if (role != null)
			claims.put(Constants.USER_ROLE_CLAIM_NAME, role);
		if (accessToken != null)
			claims.put("accessToken", accessToken);
		return Jwts.builder()
			.setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expirePeriod))
			.signWith(key, SignatureAlgorithm.HS512) // HS512 알고리즘 사용
			.compact();
	}

	public String getOriginalAccessToken(String token) {
		Claims claims = getTokenBody(token);
		return claims.get("accessToken", String.class);
	}
}