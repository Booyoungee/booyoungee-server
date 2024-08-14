package com.server.booyoungee.domain.place.domain.storePlace;

import com.server.booyoungee.domain.place.domain.Place;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "store_place")
@DiscriminatorValue("STORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StorePlace extends Place {

	@Column(nullable = false)
	private Long storeId;

	private String detailAddress;

	private String tel;

	private String mainBusiness;

	private String mapX;
	private String mapY;

	public void updateMap(String mapX, String maxY) {
		this.mapX = mapX;
		this.mapY = maxY;
	}
}
