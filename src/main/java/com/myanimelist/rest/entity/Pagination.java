package com.myanimelist.rest.entity;

public class Pagination {

	private int last_visible_page;
	private boolean has_next_page;
	private int current_page;

	public Pagination() {

	}

	public int getLast_visible_page() {
		return last_visible_page;
	}

	public void setLast_visible_page(int last_visible_page) {
		this.last_visible_page = last_visible_page;
	}

	public boolean isHas_next_page() {
		return has_next_page;
	}

	public void setHas_next_page(boolean has_next_page) {
		this.has_next_page = has_next_page;
	}

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	@Override
	public String toString() {
		return "Pagination [last_visible_page=" + last_visible_page + ", has_next_page=" + has_next_page
				+ ", current_page=" + current_page + "]";
	}
}
