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
	private Long id;

	@Column(nullable = false)
	private String movieName;

	private String movieCode;

	@Column(nullable = false)
	private String locationName;

	@Column(nullable = false)
	private String locationAddress;

	private String description;

	private String area;

	@Column(nullable = false)
	private String mapx;

	@Column(nullable = false)
	private String mapy;

	private String productedAt;
}
