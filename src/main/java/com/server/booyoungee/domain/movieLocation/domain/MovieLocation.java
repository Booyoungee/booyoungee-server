package com.server.booyoungee.domain.movieLocation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "movie_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id

	@Column(nullable = false)
	private String movieName; // 영화 제목

	private String movieCode; // 영화 코드

	@Column(nullable = false)
	private String locationName; // 장소명

	@Column(nullable = false)
	private String locationAddress; // 주소

	private String description; // 설명

	private String area; // 지역

	@Column(nullable = false)
	private String mapx; // x좌표

	@Column(nullable = false)
	private String mapy; // y좌표

	private String productedAt; // 제작년도
}
