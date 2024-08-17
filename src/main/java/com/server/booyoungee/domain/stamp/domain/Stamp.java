package com.server.booyoungee.domain.stamp.domain;

import java.time.LocalDateTime;

import com.server.booyoungee.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
	uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "placeId"})},
	indexes = {
		@Index(name = "idx_user_id", columnList = "userId"),
		@Index(name = "idx_place_id", columnList = "placeId")})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stamp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stampId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String placeId;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column()
	private String type;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

}
