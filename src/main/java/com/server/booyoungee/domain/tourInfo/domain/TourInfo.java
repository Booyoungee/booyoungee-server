package com.server.booyoungee.domain.tourInfo.domain;

import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@Table(name = "tour_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourInfo {

	@Id
	@Column(
		name = "content_id",
		updatable = false,
		nullable = false,
		unique = true
	)
	private String contentId;

	@Column(nullable = false)
	private Long views;

	@Enumerated(EnumType.STRING)
	private TourContentType contentTypeId;

	public void increaseViewCount() {
		this.views++;
	}

}
