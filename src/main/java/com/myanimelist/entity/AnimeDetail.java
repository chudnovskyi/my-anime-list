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
 * A new feature has been added - the ability to add anime to tabs like `viewed`, `favorites`, etc. 
 * The reason for this is that the application should have a separate tab to view all anime 
 * by certain parameters such as `dropped`. To display information for each anime, 
 * multiple requests to the JikanAPI would be required. 
 * 
 * However, the JikanAPI server only allows for three requests per second. To mitigate this issue, 
 * a new entity has been created in the database to store minimal information about each anime 
 * (id and title) that has been added to tabs like `viewed`, etc. 
 * 
 * The list of favorite anime will be retrieved from the database, but additional details will be 
 * obtained from the JikanAPI based on user requests. The database will only store the id and name of 
 * the anime and will not store any extra information.
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
	private Integer malId;

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
	private Set<UserAnimeDetail> userAnimeDetails = new HashSet<>();
}
