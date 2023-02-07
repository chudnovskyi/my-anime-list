package com.myanimelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.AnimeDetail;

@Repository
public interface AnimeDetailRepository extends JpaRepository<AnimeDetail, Integer> {

	AnimeDetail findByMalId(int malId);
}
