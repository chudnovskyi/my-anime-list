package com.myanimelist.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ReviewView {

	@NonNull
	private Integer animeId;

	@NotNull(message = "{Review.NotNull}")
	@Size(min = 5, message = "{Review.Size}")
	private String content;
}
