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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "anime")
public class AnimeDetail {

	@Id
	@NonNull
	@Column(name = "mal_id")
	private Integer mal_id;

	@NonNull
	@Column(name = "title")
	private String title;

	@NonNull
	@Column(name = "image")
	private String image;

	@NonNull
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
}
