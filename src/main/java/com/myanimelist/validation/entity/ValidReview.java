package com.myanimelist.validation.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ValidReview {

	private int animeId;

	@NotNull(message = "{Review.NotNull}")
	@Size(min = 5, message = "{Review.Size}")
	private String content;

	public ValidReview() {

	}

	public ValidReview(int animeId) {
		this.animeId = animeId;
	}

	public int getAnimeId() {
		return animeId;
	}

	public void setAnimeId(int animeId) {
		this.animeId = animeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
