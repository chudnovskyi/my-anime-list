package com.myanimelist.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NonNull
	@Column(name = "anime_id")
	private Integer animeId;

	@NonNull
	@Column(name = "content")
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
