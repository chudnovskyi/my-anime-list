package com.myanimelist.repository;

import com.myanimelist.entity.AnimeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeDetailRepository extends JpaRepository<AnimeDetail, Integer> {
	
}
