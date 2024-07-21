package com.server.booyoungee.domain.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.booyoungee.domain.store.domain.Store;

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

	@JsonProperty("시도")
	private String city;

	@JsonProperty("업체명")
	private String businessName;

	@JsonProperty("연락처")
	private String contactNumber;

	@JsonProperty("주요사업")
	private String mainBusiness;
	@JsonProperty("조회수")
	private int views;

	@Builder
	public StoreResponseDto(Store store) {
		this.storeId = store.getStoreId();
		this.basicAddress = store.getBasicAddress();
		this.detailedAddress = store.getDetailedAddress();
		this.district = store.getDistrict();
		this.city = store.getCity();
		this.businessName = store.getBusinessName();
		this.contactNumber = store.getContactNumber();
		this.mainBusiness = store.getMainBusiness();
		this.views = store.getViews();
	}
}
