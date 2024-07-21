package com.server.booyoungee.domain.stamp.domain;

public enum PlaceType {
	movie("movie"),
	tour("tour"),
	store("store");

	private final String key;

	PlaceType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
