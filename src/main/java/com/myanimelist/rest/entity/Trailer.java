package com.myanimelist.rest.entity;

public class Trailer {

	private String embed_url;

	public Trailer() {

	}

	public String getEmbed_url() {
		return embed_url;
	}

	public void setEmbed_url(String embed_url) {
		this.embed_url = embed_url;
	}

	@Override
	public String toString() {
		return "Trailer [embed_url=" + embed_url + "]";
	}
}
