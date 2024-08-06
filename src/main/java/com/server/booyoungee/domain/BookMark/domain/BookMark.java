package com.server.booyoungee.domain.BookMark.domain;

import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@AllArgsConstructor
@Table(name = "book_mark",
	indexes = {
		@Index(name = "idx_user_id", columnList = "user_id"),
		@Index(name = "idx_place_id", columnList = "place_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookMark;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	@Column(nullable = false)
	private Long placeId;
	@Column(nullable = false)
	private PlaceType type;
}
