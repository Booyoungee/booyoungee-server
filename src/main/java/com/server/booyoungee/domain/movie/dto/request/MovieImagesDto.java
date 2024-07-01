package com.server.booyoungee.domain.movie.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data

public class MovieImagesDto {
	private List<BackDrops> backdrops;
	private String id;

	@Getter
	@Setter
	public static class BackDrops {
		@JsonProperty("file_path")
		private String filePath;
	}

}
