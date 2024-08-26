package com.server.booyoungee.domain.like.domain;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "\"like\"")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "place_id")
	private Place place;

	public static Like of(User user, Place place) {
		return Like.builder()
			.user(user)
			.place(place)
			.build();
	}
}
