package com.server.booyoungee.domain.place.dto.response.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StoreResponseDto {
	@JsonProperty("store_id")
	private Long storeId;

	@JsonProperty("기본주소")
	private String basicAddress;

	@JsonProperty("상세주소")
	private String detailedAddress;

	@JsonProperty("시군구")
	private String district;

	@JsonProperty("업체명")
	private String businessName;

	@JsonProperty("연락처")
	private String contactNumber;

	@JsonProperty("주요사업")
	private String mainBusiness;
	@JsonProperty("조회수")
	private Long viewCount;

	@Builder
	public StoreResponseDto(StorePlace storePlace) {
		this.storeId = storePlace.getStoreId();
		this.basicAddress = storePlace.getBasicAddress();
		this.detailedAddress = storePlace.getDetailAddress();
		this.district = storePlace.getDistrict();
		this.businessName = storePlace.getName();
		this.contactNumber = storePlace.getTel();
		this.mainBusiness = storePlace.getMainBusiness();
		this.viewCount = storePlace.getViewCount();
	}
}
