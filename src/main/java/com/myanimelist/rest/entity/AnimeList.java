package com.myanimelist.rest.entity;

import java.util.ArrayList;
import java.util.List;

public class AnimeList {

	private Pagination pagination;
	private List<Anime> data;

	{
		data = new ArrayList<>();
	}

	public AnimeList() {

	}

	public AnimeList(List<Anime> data) {
		this.data = data;
	}

	public AnimeList(Pagination pagination, List<Anime> data) {
		this.pagination = pagination;
		this.data = data;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<Anime> getData() {
		return data;
	}

	public void setData(List<Anime> data) {
		this.data = data;
	}
}
