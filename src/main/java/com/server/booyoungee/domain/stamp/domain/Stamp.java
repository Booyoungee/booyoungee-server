package com.server.booyoungee.domain.stamp.domain;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "stamp",
	uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "place"})},
	indexes = {
		@Index(name = "idx_user_id", columnList = "userId"),
		@Index(name = "idx_place_id", columnList = "placeId")})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stamp extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stampId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	private String type;

	public static Stamp of(User user, Place place, String type) {
		return Stamp.builder()
			.user(user)
			.place(place)
			.type(type)
			.build();
	}

}
