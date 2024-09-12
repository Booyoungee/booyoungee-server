package com.server.booyoungee.domain.user.domain;

import com.server.booyoungee.global.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@AllArgsConstructor
@Table(name = "blocked_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockUser extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "blocker_id")
	private User blocker;

	@ManyToOne
	@JoinColumn(name = "blocked_id")
	private User blocked;

	// Getters and Setters
	public static BlockUser of(User blocker, User blocked) {
		return BlockUser.builder()
			.blocker(blocker)
			.blocked(blocked)
			.build();
	}
}
