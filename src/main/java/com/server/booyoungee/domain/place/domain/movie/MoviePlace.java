package com.server.booyoungee.domain.place.domain.movie;

import com.server.booyoungee.domain.place.domain.Place;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "movie_place",indexes = {
	@Index(name = "idx_movie_place_map", columnList = "mapX, mapY")
})
@DiscriminatorValue("MOVIE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoviePlace extends Place {

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
