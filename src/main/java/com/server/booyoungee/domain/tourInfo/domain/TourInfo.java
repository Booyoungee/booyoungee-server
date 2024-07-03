package com.server.booyoungee.domain.tourInfo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	public void increaseViewCount() {
		this.views++;
	}

}
