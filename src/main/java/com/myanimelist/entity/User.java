package com.myanimelist.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;
	
	@ManyToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	
	@OneToMany(
			mappedBy = "user", // The field IN CLASS REVIEW that owns the relationship (has a FK). 
							   // Required unlessthe relationship is unidirectional.
			fetch = FetchType.LAZY, 
			cascade = {
				CascadeType.DETACH, 
				CascadeType.MERGE, 
				CascadeType.PERSIST, 
				CascadeType.REFRESH
			})
	private Collection<Review> reviews;
	
	@OneToMany(mappedBy = "user")
	private Set<UserAnimeDetail> userAnimeDetails = new HashSet<UserAnimeDetail>();
	
	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<UserAnimeDetail> getUserAnimeDetails() {
		return userAnimeDetails;
	}

	public void setUserAnimeDetails(Set<UserAnimeDetail> userAnimeDetails) {
		this.userAnimeDetails = userAnimeDetails;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", roles="
				+ roles + ", reviews=" + reviews + "]";
	}
}
