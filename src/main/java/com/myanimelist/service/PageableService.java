package com.myanimelist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

import java.util.List;

public interface PageableService<T> {

    void preparePageableModel(Model theModel, Page<T> animePage);

    PageImpl<T> getPageable(List<T> animeList, int page, int size);
}
