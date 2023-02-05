package com.myanimelist.rest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Jpg {

	private String image_url;
	private String small_image_url;
	private String large_image_url;
}
