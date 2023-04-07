package com.myanimelist.repository;

import com.myanimelist.entity.UserAnime;
import com.myanimelist.model.AnimeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnimeRepository extends JpaRepository<UserAnime, Integer> {

    Page<UserAnime> findAllByStatusAndUser_Username(AnimeStatus status, String username, Pageable pageable);

    Page<UserAnime> findAllByFavouriteAndUser_Username(boolean favourite, String username, Pageable pageable);

    Optional<UserAnime> findByAnime_IdAndUser_Username(Integer malId, String username);

    List<UserAnime> findAllByAnime_Id(int malId);
}
