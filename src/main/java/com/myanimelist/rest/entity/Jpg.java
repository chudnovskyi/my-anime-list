package com.myanimelist.rest.entity;

public class Jpg {

	private String image_url;
	private String small_image_url;
	private String large_image_url;

	public Jpg() {

	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getSmall_image_url() {
		return small_image_url;
	}

	public void setSmall_image_url(String small_image_url) {
		this.small_image_url = small_image_url;
	}

	public String getLarge_image_url() {
		return large_image_url;
	}

	public void setLarge_image_url(String large_image_url) {
		this.large_image_url = large_image_url;
	}

	@Override
	public String toString() {
		return "Jpg [image_url=" + image_url + ", small_image_url=" + small_image_url + ", large_image_url="
				+ large_image_url + "]";
	}
}
