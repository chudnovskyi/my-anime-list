package com.myanimelist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "anime")
public class AnimeDetail {

	@Id
	@NonNull
	private Integer malId;

	@NonNull
	private String title;

	@NonNull
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
