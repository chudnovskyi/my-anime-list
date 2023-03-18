package com.myanimelist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users_anime")
public class UserAnimeDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH
			})
	@JoinColumn(name = "user_id")
	private User user;

	/* 
	 * Why after manually selecting all 5 or 4 (except remove) 
	 * cascade types there's an exceptions when adding anime in any tab?
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mal_id")
	private AnimeDetail animeDetail;

	private int score;

	private boolean favourite;
	private boolean watching;
	private boolean planning;
	private boolean completed;
	private boolean onHold;
	private boolean dropped;

	public void setAsWatching() {
		setParamsToFalse();
		watching = true;
	}

	public void setAsPlanning() {
		setParamsToFalse();
		setFavourite(false);
		setScore(0);
		planning = true;
	}

	public void setAsCompleted() {
		setParamsToFalse();
		completed = true;
	}

	public void setAsOnHold() {
		setParamsToFalse();
		setFavourite(false);
		onHold = true;
	}

	public void setAsDropped() {
		setParamsToFalse();
		setFavourite(false);
		dropped = true;
	}

	private void setParamsToFalse() {
		setCompleted(false);
		setDropped(false);
		setOnHold(false);
		setWatching(false);
		setPlanning(false);
	}
}
