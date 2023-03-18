package com.myanimelist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NonNull
	private Integer animeId;

	@NonNull
	private String content;

	@NonNull
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
}
