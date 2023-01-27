package com.myanimelist.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PageableServiceImpl implements PageableService {

	@Override
	public <E> void preparePegableModel(Model theModel, Page<E> page) {
		theModel.addAttribute("page", page);

		int totalPages = page.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			theModel.addAttribute("pageNumbers", pageNumbers);
		}
	}

	@Override
	public <E> PageImpl<E> getPegable(List<E> list, int pageNumber, int pageSize) {
		PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);

		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		int listSize = list.size();

		if (listSize < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, listSize);
			list = list.subList(startItem, toIndex);
		}

		return new PageImpl<E>(list, PageRequest.of(currentPage, pageSize), listSize);
	}
}
