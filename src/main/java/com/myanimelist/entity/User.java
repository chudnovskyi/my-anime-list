package com.myanimelist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private String email;

	private String activationCode;

	@Lob
	private byte[] image;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH
			})
	@JoinTable(
			name = "users_roles", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.DETACH, 
					CascadeType.MERGE, 
					CascadeType.PERSIST, 
					CascadeType.REFRESH
			})
	private Collection<Review> reviews;

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH
			})
	private Set<UserAnimeDetail> userAnimeDetails = new HashSet<>();
}
