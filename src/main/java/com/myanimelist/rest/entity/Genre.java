package com.myanimelist.rest.entity;

public class Genre {

	private int mal_id;
	private String name;

	public Genre() {

	}

	public int getMal_id() {
		return mal_id;
	}

	public void setMal_id(int mal_id) {
		this.mal_id = mal_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Genre [mal_id=" + mal_id + ", name=" + name + "]";
	}
}
