package com.myanimelist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

public class PageableServiceTest {

	private PageableServiceImpl pageableService = new PageableServiceImpl();

	@Test
	public void preparePageableModelTest() {
		Model theModel = mock(Model.class);
		Page<String> page = new PageImpl<>(Arrays.asList("a", "b", "c"));

		pageableService.preparePageableModel(theModel, page);

		verify(theModel, times(1)).addAttribute("page", page);
		verify(theModel, times(1)).addAttribute("pageNumbers", Arrays.asList(1));
	}

	@Test
	public void getPageableTest() {
		List<String> list = Arrays.asList("a", "b", "c", "d", "e");
		int pageNumber = 2;
		int pageSize = 2;

		PageImpl<String> expectedPage = new PageImpl<>(Arrays.asList("c", "d"),
				PageRequest.of(pageNumber - 1, pageSize), list.size());

		PageImpl<String> actualPage = pageableService.getPageable(list, pageNumber, pageSize);

		assertEquals(expectedPage, actualPage);
	}
	
	@Test
	public void getPageableEmptyTest() {
		List<String> list = Arrays.asList();
		int pageNumber = 2;
		int pageSize = 2;

		PageImpl<String> expectedPage = new PageImpl<>(Arrays.asList(),
				PageRequest.of(pageNumber - 1, pageSize), list.size());

		PageImpl<String> actualPage = pageableService.getPageable(list, pageNumber, pageSize);

		assertEquals(expectedPage, actualPage);
	}
}
