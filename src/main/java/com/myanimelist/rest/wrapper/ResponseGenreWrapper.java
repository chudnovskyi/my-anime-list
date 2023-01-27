package com.myanimelist.rest.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.myanimelist.rest.entity.Genre;

public class ResponseGenreWrapper {

	private List<Genre> data;

	{
		data = new ArrayList<>();
	}

	public ResponseGenreWrapper() {

	}

	public List<Genre> getData() {
		return data;
	}

	public void setData(List<Genre> data) {
		this.data = data;
	}
}
