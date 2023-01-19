package com.myanimelist.rest.entity;

import java.util.ArrayList;
import java.util.List;

public class AnimeList {
	private List<Anime> data;

	public AnimeList() {
		data = new ArrayList<>();
	}
	
	public AnimeList(List<Anime> data) {
		this.data = data;
	}

	public List<Anime> getData() {
		return data;
	}

	public void setData(List<Anime> data) {
		this.data = data;
	}
}
