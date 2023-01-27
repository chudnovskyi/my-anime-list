package com.myanimelist.rest.entity;

public class Images {

	private Jpg jpg;

	public Images() {

	}

	public Jpg getJpg() {
		return jpg;
	}

	public void setJpg(Jpg jpg) {
		this.jpg = jpg;
	}

	@Override
	public String toString() {
		return "Images [jpg=" + jpg + "]";
	}
}
