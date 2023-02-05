package com.myanimelist.rest.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.myanimelist.rest.entity.Anime;
import com.myanimelist.rest.entity.Pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseAnimeWrapper {

	private Pagination pagination;
	private List<Anime> data;

	{
		data = new ArrayList<>();
	}
}
