package com.myanimelist.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * You can extract anime by sending a request to the JikanAPI, 
 * why did you create a new table in the database?
 * 
 * Answer: A new feature - adding anime to the tab `viewed`, `favorites` etc. 
 * Because the application should have a separate tab where you can see all the 
 * anime by some parameter eg `dropped`, then you will need lot of requests 
 * to the JikanAPI in order to display information for each anime using it's id.
 * 
 * BUT, the JikanAPI server provides only 3 requests per second. I decided to create 
 * a new entity in my database that will store the minimum information about each
 * anime (id, title) that only been added to the tab `viewed` and so on.
 * 
 * Then, the entire list of favorite anime will be taken from the database, 
 * while the database will not store anything extra, except for the id and 
 * name of the anime, and additional details will be taken according to the user's request.
 */
@Entity
@Table(name = "anime")
public class AnimeDetail {

	@Id
	@Column(name = "mal_id")
	private int mal_id;

	@Column(name = "title")
	private String title;

	@Column(name = "image")
	private String image;

	@OneToMany(
			mappedBy = "animeDetail",
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH
			})
	private Set<UserAnimeDetail> userAnimeDetails = new HashSet<UserAnimeDetail>();

	public AnimeDetail() {

	}

	public AnimeDetail(int mal_id, String title, String image) {
		this.mal_id = mal_id;
		this.title = title;
		this.image = image;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<UserAnimeDetail> getUserAnimeDetails() {
		return userAnimeDetails;
	}

	public void setUserAnimeDetails(Set<UserAnimeDetail> userAnimeDetails) {
		this.userAnimeDetails = userAnimeDetails;
	}

	@Override
	public String toString() {
		return "AnimeDetails [mal_id=" + mal_id + ", title=" + title + "]";
	}
}
