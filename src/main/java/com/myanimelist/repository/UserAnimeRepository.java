package com.myanimelist.repository;

import com.myanimelist.entity.UserAnime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnimeRepository extends JpaRepository<UserAnime, Integer> {

    List<UserAnime> findAllByUser_UsernameOrderByScoreDesc(String username);

    Optional<UserAnime> findByAnime_IdAndUser_Username(Integer malId, String username);

    List<UserAnime> findAllByAnime_Id(int malId);
}
