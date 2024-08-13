package com.server.booyoungee.domain.place.domain;

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

	public static PlaceType getKey(String key) {
		if (store.getKey().equals(key))
			return store;
		else if (movie.equals(key))
			return movie;
		else
			return tour;

	}

}
