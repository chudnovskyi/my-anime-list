package com.myanimelist.rest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pagination {

	private int last_visible_page;
	private boolean has_next_page;
	private int current_page;
}
