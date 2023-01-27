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

	public UserAnimeDetail() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AnimeDetail getAnimeDetail() {
		return animeDetail;
	}

	public void setAnimeDetail(AnimeDetail animeDetail) {
		this.animeDetail = animeDetail;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public boolean isWatching() {
		return watching;
	}

	public void setWatching(boolean watching) {
		if (watching == true) {
			setParamsToFalse();
		}
		this.watching = watching;
	}
	
	public boolean isPlanning() {
		return planning;
	}

	public void setPlanning(boolean planning) {
		if (planning == true) {
			setParamsToFalse();
			setFavourite(false);
			setScore(0);
		}
		this.planning = planning;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		if (completed == true) {
			setParamsToFalse();
		}
		this.completed = completed;
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setOnHold(boolean onHold) {
		if (onHold == true) {
			setParamsToFalse();
			setFavourite(false);
		}
		this.onHold = onHold;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		if (dropped == true) {
			setParamsToFalse();
			setFavourite(false);
		}
		this.dropped = dropped;
	}

	private void setParamsToFalse() {
		setCompleted(false);
		setDropped(false);
		setOnHold(false);
		setWatching(false);
		setPlanning(false);
	}

	@Override
	public String toString() {
		return "UserAnimeDetail [id=" + id + ", user=" + user + ", animeDetail=" + animeDetail + ", score=" + score
				+ ", favourite=" + favourite + ", watching=" + watching + ", planning=" + planning + ", completed="
				+ completed + ", onHold=" + onHold + ", dropped=" + dropped + "]";
	}
}
