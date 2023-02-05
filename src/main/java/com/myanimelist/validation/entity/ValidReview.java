package com.myanimelist.validation.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ValidReview {

	@NonNull
	private Integer animeId;

	@NotNull(message = "{Review.NotNull}")
	@Size(min = 5, message = "{Review.Size}")
	private String content;
}
