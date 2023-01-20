package com.myanimelist.rest.wrapper;

import com.myanimelist.rest.entity.Anime;

public class SingleAnimeWrapper {

	private Anime data;

	public SingleAnimeWrapper() {
		
	}

	public Anime getData() {
		return data;
	}

	public void setData(Anime data) {
		this.data = data;
	}
}
