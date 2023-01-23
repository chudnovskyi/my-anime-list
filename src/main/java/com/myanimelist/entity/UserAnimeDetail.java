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

	@Override
	public String toString() {
		return "UserAnimeDetail [id=" + id + ", user=" + user + ", animeDetail=" + animeDetail + ", score=" + score
				+ ", favourite=" + favourite + "]";
	}
}
