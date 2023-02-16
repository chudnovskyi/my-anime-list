package com.myanimelist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

public interface PageableService {

	<E> void preparePageableModel(Model theModel, Page<E> animePage);

	<E> PageImpl<E> getPageable(List<E> animeList, int page, int size);
}
