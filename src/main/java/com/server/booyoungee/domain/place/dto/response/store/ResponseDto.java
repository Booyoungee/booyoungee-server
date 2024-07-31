package com.server.booyoungee.domain.place.dto.response.store;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class ResponseDto {

	@JsonProperty("currentCount")
	private int currentCount;

	@JsonProperty("data")
	private List<StoreResponseDto> data;

	@JsonProperty("matchCount")
	private int matchCount;

	@JsonProperty("page")
	private int page;

	@JsonProperty("perPage")
	private int perPage;

	@JsonProperty("totalCount")
	private int totalCount;
}
