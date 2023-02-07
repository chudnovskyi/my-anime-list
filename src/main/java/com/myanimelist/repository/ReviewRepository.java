package com.myanimelist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>  {

	List<Review> findAllByAnimeId(int animeId);
}
