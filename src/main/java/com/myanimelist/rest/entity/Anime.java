package com.myanimelist.rest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Anime {

	private int mal_id;
	private int episodes;
	private int rank;
	private int year;

	private double score;

	private String title;
	private String type;
	private String status;
	private String rating;

	private String synopsis;
	private String background;

	private Images images;

	private Trailer trailer;
}
