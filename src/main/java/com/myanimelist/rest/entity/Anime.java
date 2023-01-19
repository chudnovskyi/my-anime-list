package com.myanimelist.rest.entity;

public class Anime {

	private int mal_id;
	private String title;
	private String rating;

	public Anime() {
		
	}
	
	public Anime(int mal_id, String title, String rating) {
		this.mal_id = mal_id;
		this.title = title;
		this.rating = rating;
	}

	public int getMal_id() {
		return mal_id;
	}

	public void setMal_id(int mal_id) {
		this.mal_id = mal_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Data [mal_id=" + mal_id + ", title=" + title + ", rating=" + rating + "]";
	}
}
