package com.server.booyoungee.domain.place.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.server.booyoungee.domain.bookmark.domain.BookMark;
import com.server.booyoungee.domain.like.domain.Like;
import com.server.booyoungee.domain.review.comment.domain.Comment;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "place")
@DiscriminatorColumn(name = "D_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String basicAddress;

	private String district;

	@ColumnDefault("0")
	private int viewCount;

	@OneToMany(mappedBy = "place")
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "placeId")
	private List<BookMark> bookmarks = new ArrayList<>();

	@OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
	private List<Like> likes = new ArrayList<>();

	public void increaseViewCount() {
		this.viewCount++;
	}
}
