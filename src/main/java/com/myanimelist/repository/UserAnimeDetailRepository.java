package com.myanimelist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.UserAnimeDetail;

@Repository
public interface UserAnimeDetailRepository extends JpaRepository<UserAnimeDetail, Integer> {

	List<UserAnimeDetail> findAllByUser_UsernameOrderByScoreDesc(String username);
	
	Optional<UserAnimeDetail> findByAnimeDetail_MalIdAndUser_Username(Integer malId, String username);
	
	List<UserAnimeDetail> findAllByAnimeDetail_MalId(int malId);
}
