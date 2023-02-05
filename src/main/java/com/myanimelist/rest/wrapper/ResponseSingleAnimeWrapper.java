package com.myanimelist.rest.wrapper;

import com.myanimelist.rest.entity.Anime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseSingleAnimeWrapper {

	private Anime data;
}
