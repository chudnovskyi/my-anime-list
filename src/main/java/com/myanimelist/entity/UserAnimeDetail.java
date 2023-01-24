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

	// why after changing CascadeType there's an exceptions?
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
	private boolean on_hold;
	
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
		setParamsToFalse();
		this.watching = watching;
	}
	
	public boolean isPlanning() {
		return planning;
	}

	public void setPlanning(boolean planning) {
		setParamsToFalse();
		setFavourite(false);
		setScore(0);
		this.planning = planning;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		setParamsToFalse();
		this.completed = completed;
	}

	public boolean isOn_hold() {
		return on_hold;
	}

	public void setOn_hold(boolean on_hold) {
		setParamsToFalse();
		setFavourite(false);
		this.on_hold = on_hold;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		setParamsToFalse();
		setFavourite(false);
		this.dropped = dropped;
	}
	
	// most likely it will be refactored.
	private void setParamsToFalse() {
		completed = false;
		dropped = false;
		on_hold = false;
		watching = false;
		planning = false;;
	}

	@Override
	public String toString() {
		return "UserAnimeDetail [id=" + id + ", score=" + score + ", favourite=" + favourite + ", watching=" + watching
				+ ", planning=" + planning + ", completed=" + completed + ", on_hold=" + on_hold + ", dropped="
				+ dropped + "]";
	}
}
