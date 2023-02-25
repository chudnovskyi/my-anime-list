package com.myanimelist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.myanimelist.entity.Review;
import com.myanimelist.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;

	private User testUser = new User();
	
	@BeforeEach
	public void setUp() {
		reviewRepository.deleteAll();
		
		testUser.setUsername("testuser");
		testUser.setEmail("email");
		testUser.setPassword("pass");
		userRepository.save(testUser);
	}

	@Test
	public void testFindAllByAnimeId() {
		Review review1 = new Review();
		review1.setAnimeId(-10);
		review1.setContent("Great!");
		review1.setUser(testUser);
		reviewRepository.save(review1);

		Review review2 = new Review();
		review2.setAnimeId(-10);
		review2.setContent("Good.");
		review2.setUser(testUser);
		reviewRepository.save(review2);

		Review review3 = new Review();
		review3.setAnimeId(0);
		review3.setContent("Bad...");
		review3.setUser(testUser);
		reviewRepository.save(review3);

		List<Review> reviewsForAnime = reviewRepository.findAllByAnimeId(-10);

		assertThat(reviewsForAnime).hasSize(2);
		assertThat(reviewsForAnime).containsExactly(review1, review2);
	}

	@Test
	public void testFindAll() {
		List<Review> reviews = new ArrayList<>();
		Review review1 = new Review();
		review1.setAnimeId(-10);
		review1.setContent("Great!");
		review1.setUser(testUser);
		reviewRepository.save(review1);
		reviews.add(review1);

		Review review2 = new Review();
		review2.setAnimeId(-10);
		review2.setContent("Good.");
		review2.setUser(testUser);
		reviewRepository.save(review2);
		reviews.add(review2);

		Review review3 = new Review();
		review3.setAnimeId(0);
		review3.setContent("Bad...");
		review3.setUser(testUser);
		reviewRepository.save(review3);
		reviews.add(review3);

		List<Review> result = reviewRepository.findAll();

		assertThat(result).hasSize(reviews.size());
		assertThat(result).containsAll(reviews);
	}
}
