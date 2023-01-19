package com.myanimelist.rest.entity;

import java.util.ArrayList;
import java.util.List;

public class ResponseAnimeWrapper {

	private Pagination pagination;
	private List<Anime> data;

	{
		data = new ArrayList<>();
	}

	public ResponseAnimeWrapper() {

	}

	public ResponseAnimeWrapper(List<Anime> data) {
		this.data = data;
	}

	public ResponseAnimeWrapper(Pagination pagination, List<Anime> data) {
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
