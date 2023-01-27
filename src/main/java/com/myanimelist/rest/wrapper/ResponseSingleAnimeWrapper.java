package com.myanimelist.rest.wrapper;

import com.myanimelist.rest.entity.Anime;

public class ResponseSingleAnimeWrapper {

	private Anime data;

	public ResponseSingleAnimeWrapper() {

	}

	public Anime getData() {
		return data;
	}

	public void setData(Anime data) {
		this.data = data;
	}
}
