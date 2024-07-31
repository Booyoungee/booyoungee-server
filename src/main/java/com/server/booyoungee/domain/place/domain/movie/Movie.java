package com.server.booyoungee.domain.place.domain.movie;

import com.server.booyoungee.domain.place.domain.Place;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "movie")
@DiscriminatorValue("MOVIE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Place {

	@Column(nullable = false)
	private String movieName;

	private String movieCode;

	private String description;

	@Column(nullable = false)
	private String mapX;

	@Column(nullable = false)
	private String mapY;

	private String productionYear;
}
