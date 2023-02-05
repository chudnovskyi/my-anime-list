package com.myanimelist.rest.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.myanimelist.rest.entity.Genre;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseGenreWrapper {

	private List<Genre> data;

	{
		data = new ArrayList<>();
	}
}
