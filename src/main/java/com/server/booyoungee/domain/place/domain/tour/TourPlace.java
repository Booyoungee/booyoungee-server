package com.server.booyoungee.domain.place.domain.tour;

import com.server.booyoungee.domain.place.domain.Place;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "tour_place")
@DiscriminatorValue("TOUR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourPlace extends Place {

	private String contentId;

	private String contentTypeId;

	public static TourPlace of(String contentId, String contentTypeId) {
		return TourPlace.builder()
			.contentId(contentId)
			.contentTypeId(contentTypeId)
			.build();
	}
}
