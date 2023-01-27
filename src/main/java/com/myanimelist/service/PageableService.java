package com.myanimelist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

public interface PageableService {

	public <E> void preparePegableModel(Model theModel, Page<E> animePage);

	public <E> PageImpl<E> getPegable(List<E> animeList, int page, int size);
}
