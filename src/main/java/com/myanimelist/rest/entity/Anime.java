package com.myanimelist.rest.entity;

public class Anime {

	private int mal_id;
	private int episodes;
	private int rank;
	private int year;

	private double score;

	private String title;
	private String type;
	private String status;
	private String rating;

	private String synopsis;
	private String background;

	private Images images;

	private Trailer trailer;

	public Anime() {

	}

	public int getMal_id() {
		return mal_id;
	}

	public void setMal_id(int mal_id) {
		this.mal_id = mal_id;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public Trailer getTrailer() {
		return trailer;
	}

	public void setTrailer(Trailer trailer) {
		this.trailer = trailer;
	}

	@Override
	public String toString() {
		return "Anime [mal_id=" + mal_id + ", episodes=" + episodes + ", rank=" + rank + ", year=" + year + ", score="
				+ score + ", title=" + title + ", type=" + type + ", status=" + status + ", rating=" + rating
				+ ", synopsis=" + synopsis + ", background=" + background + ", images=" + images + ", trailer="
				+ trailer + "]";
	}
}
