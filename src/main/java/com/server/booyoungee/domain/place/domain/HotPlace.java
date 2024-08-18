package com.server.booyoungee.domain.place.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
public class HotPlace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long placeId;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	@Column(nullable = false, columnDefinition = "0")
	private int viewCount;

	@Column(nullable = false)
	private boolean isHotPlace;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public static HotPlace from(Long placeId, String type, String name, int viewCount) {
		return HotPlace.builder()
			.placeId(placeId)
			.type(type)
			.name(name)
			.viewCount(viewCount)
			.isHotPlace(true)
			.build();
	}

	// Builder 패턴 사용을 위해 추가된 빌더 메서드

	public static HotPlace of(
		Long placeId, String type, String name, int viewCount, boolean isHotPlace
	) {
		return HotPlace.builder()
			.placeId(placeId)
			.type(type)
			.name(name)
			.viewCount(viewCount)
			.isHotPlace(isHotPlace)
			.build();
	}

}
