package com.myanimelist.validation.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ValidSearchAnime {

	@NotNull(message = "{Search.Anime.NotNull}")
	@Size(min = 3, message = "{Search.Anime.Size}")
	private String title;

	public ValidSearchAnime() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
