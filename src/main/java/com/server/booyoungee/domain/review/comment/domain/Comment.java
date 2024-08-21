package com.server.booyoungee.domain.review.comment.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import org.hibernate.annotations.ColumnDefault;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.review.comment.dto.request.CommentRequest;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String content;

	@Embedded
	private Stars stars;

	@ColumnDefault("0")
	private int accuseCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	public static Comment of(User user, Place place, String content, int stars) {
		return Comment.builder()
			.content(content)
			.stars(Stars.of(stars))
			.writer(user)
			.place(place)
			.build();
	}
}
