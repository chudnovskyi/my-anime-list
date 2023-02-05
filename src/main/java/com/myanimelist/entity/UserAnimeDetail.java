package com.myanimelist.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

	@Column(name = "score")
	private int score;

	@Column(name = "favourite")
	private boolean favourite;

	@Column(name = "watching")
	private boolean watching;

	@Column(name = "planning")
	private boolean planning;

	@Column(name = "completed")
	private boolean completed;

	@Column(name = "on_hold")
	private boolean onHold;

	@Column(name = "dropped")
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
