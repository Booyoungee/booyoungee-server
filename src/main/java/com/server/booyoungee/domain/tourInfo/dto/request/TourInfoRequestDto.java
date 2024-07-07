package com.server.booyoungee.domain.tourInfo.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourInfoRequestDto {
	@JsonProperty("response")
	private ResponseDto response;

	@Getter
	@Setter
	public static class ResponseDto {
		@JsonProperty("header")
		private HeaderDto header;
		@JsonProperty("body")
		private BodyDto body;

		@Getter
		@Setter
		public static class HeaderDto {
			@JsonProperty("resultCode")
			private String resultCode;
			@JsonProperty("resultMsg")
			private String resultMsg;
		}

		@Getter
		@Setter
		public static class BodyDto {
			@JsonProperty("items")
			private ItemsDto items;

			@Getter
			@Setter
			public static class ItemsDto {
				@JsonProperty("item")
				private List<ItemDto> item;

				@Getter
				@Setter
				public static class ItemDto {
					@JsonProperty("addr1")
					private String addr1;
					@JsonProperty("contentid")
					private String contentId;
					@JsonProperty("firstimage")
					private String firstImage;
					@JsonProperty("firstimage2")
					private String firstImage2;
					@JsonProperty("mapx")
					private String mapX;
					@JsonProperty("mapy")
					private String mapY;
					@JsonProperty("title")
					private String title;
					@JsonProperty("tel")
					private String tel;
				}
			}

			@JsonProperty("numOfRows")
			private int numOfRows;
			@JsonProperty("pageNo")
			private int pageNo;
			@JsonProperty("totalCount")
			private int totalCount;
		}
	}
}
