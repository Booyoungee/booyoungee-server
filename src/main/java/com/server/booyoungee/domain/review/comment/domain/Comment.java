package com.server.booyoungee.domain.review.comment.domain;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.review.accuse.domain.Accuse;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "comment", indexes = {
	@Index(name = "idx_place_id", columnList = "place_id"),
	@Index(name = "idx_stars", columnList = "stars")
})
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

	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "user_id")
	private User writer;

	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "place_id")
	private Place place;

	@ElementCollection(fetch = LAZY)
	@CollectionTable(
		name = "accuse",
		joinColumns = @JoinColumn(name = "comment_id")
	)
	private List<Accuse> accuseList = new ArrayList<>();

	public static Comment of(User user, Place place, String content, int stars) {
		return Comment.builder()
			.content(content)
			.stars(Stars.of(stars))
			.writer(user)
			.place(place)
			.build();
	}

	public void accuseReview(User user) {
		Accuse accuse = new Accuse(user.getUserId());
		accuseList.add(accuse);
	}
}
