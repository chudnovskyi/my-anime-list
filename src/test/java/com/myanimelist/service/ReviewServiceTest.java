package com.myanimelist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myanimelist.authentication.AuthenticationFacade;
import com.myanimelist.entity.Review;
import com.myanimelist.entity.User;
import com.myanimelist.exception.UserHasNoAccessException;
import com.myanimelist.repository.ReviewRepository;
import com.myanimelist.validation.entity.ValidReview;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

	@Mock
	private ReviewRepository reviewRepository;
	@Mock
	private UserService userService;
	@Mock
	private AuthenticationFacade authenticationFacade;
	
	@InjectMocks
	private ReviewServiceImpl reviewService;
	
	private Review review;

	@BeforeEach
	public void setUp() {
		review = getReview();
	}
	
	@Test
	void findReviewsTest() {
		doReturn(List.of(review)).when(reviewRepository).findAllByAnimeId(review.getAnimeId());
		
		assertThat(reviewService.findReviews(review.getAnimeId())).hasSize(1);
		assertThat(reviewService.findReviews(review.getAnimeId()).get(0)).isEqualTo(review);
	}
	
	@Test
	void findNoReviewsTest() {
		doReturn(List.of()).when(reviewRepository).findAllByAnimeId(anyInt());
		
		assertThat(reviewService.findReviews(anyInt())).isEmpty();
	}
	
    @Test
    public void saveTest() {
    	Review review = getReview();
    
		ValidReview validReview = new ValidReview();
		validReview.setAnimeId(review.getAnimeId());
		validReview.setContent(review.getContent());
		
		doReturn(review.getUser().getUsername()).when(authenticationFacade).getUsername();
		doReturn(review.getUser()).when(userService).find(review.getUser().getUsername());
		
		reviewService.save(validReview);
		
		verify(reviewRepository, times(1)).save(review);
		verify(authenticationFacade, times(1)).getUsername();
		verify(userService, times(1)).find(review.getUser().getUsername());
    }
	
	@Test
	void removeSuccess() {
		doReturn(Optional.of(review)).when(reviewRepository).findById(review.getId());
		doReturn(review.getUser().getUsername()).when(authenticationFacade).getUsername();
		
		reviewService.remove(review.getId());
		
		verify(reviewRepository).deleteById(review.getId());
	}

	@Test
	void removeFail() {
		assertAll(
				() -> assertThrows(EntityNotFoundException.class, () -> reviewService.remove(Integer.MAX_VALUE)),
				() -> assertThrows(EntityNotFoundException.class, () -> reviewService.remove(null)),
				() -> {
					doReturn(Optional.of(review)).when(reviewRepository).findById(review.getId());
					doReturn("dummy").when(authenticationFacade).getUsername();
					
					assertThrows(UserHasNoAccessException.class, () -> reviewService.remove(review.getId()));
				}
		);
	}
	
	private Review getReview() {
		Review review = new Review();
		review.setAnimeId(1);
		review.setContent("nice");
		review.setUser(getUser());
		return review;
	}

	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setUsername("user");
		user.setEmail("user@gmail.com");
		user.setPassword("$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC");
		user.setActivationCode("activate");
		user.setReviews(Collections.emptyList());
		return user;
	}
}
