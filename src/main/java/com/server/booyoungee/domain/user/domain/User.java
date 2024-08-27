package com.server.booyoungee.domain.user.domain;

import java.util.ArrayList;
import java.util.List;

import com.server.booyoungee.domain.bookmark.domain.BookMark;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false)
	private String serialId;

	@Column
	private String refreshToken;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "writer")
	List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<BookMark> bookMarks = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Stamp> stamps = new ArrayList<>();

	public enum Role {
		USER,
		ADMIN
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void updateName(String name) {
		this.name = name;
	}
}