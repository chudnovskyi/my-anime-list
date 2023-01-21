package com.myanimelist.validation.entity;

public class ValidReview {

	private int animeId;
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
