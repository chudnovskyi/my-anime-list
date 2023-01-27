package com.myanimelist.rest.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.Pagination;

public class ResponseAnimeWrapper {

	private Pagination pagination;
	private List<Anime> data;

	{
		data = new ArrayList<>();
	}

	public ResponseAnimeWrapper() {

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
