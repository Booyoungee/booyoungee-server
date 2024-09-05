package com.server.booyoungee.domain.place.domain;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.ColumnDefault;

import com.server.booyoungee.domain.place.domain.store.StorePlace;
import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hot_place")
public class HotPlace extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "place_id")
	private Place place;

	@Column(nullable = false)
	private String type;

	@ColumnDefault("true")
	private boolean isHotPlace;
	// Builder 패턴 사용을 위해 추가된 빌더 메서드

	public static HotPlace of(
		Place place, String type, boolean isHotPlace
	) {
		return HotPlace.builder()
			.isHotPlace(true)
			.place(place)
			.type(type)
			.isHotPlace(isHotPlace)
			.build();
	}

	public static HotPlace from(
		Place place
	) {
		String type;
		if (place instanceof TourPlace)
			type = "tour";
		else if (place instanceof StorePlace)
			type = "store";
		else
			type = "movie";
		return HotPlace.builder()
			.isHotPlace(true)
			.place(place)
			.type(type)
			.build();
	}

}
