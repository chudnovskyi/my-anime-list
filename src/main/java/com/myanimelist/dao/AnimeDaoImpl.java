package com.myanimelist.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.AnimeDetail;
import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.rest.entity.Anime;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.JikanApiService;
import com.myanimelist.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AnimeDaoImpl implements AnimeDao {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserService userService;

	@Autowired
	private AnimeService animeService;

	@Autowired
	private JikanApiService jikanApiService;

	@Override
	public List<UserAnimeDetail> getUserAnimeDetailList() {
		Session session = entityManager.unwrap(Session.class);
		
		return session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user_id = :theUser", 
				UserAnimeDetail.class)
			.setParameter("theUser", userService.find(getAuthUsername()))
			.getResultList()
			.stream()
			.sorted((o1, o2) -> o2.getScore() - o1.getScore())
			.toList();
	}

	@Override
	public void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer) {
		Session session = entityManager.unwrap(Session.class);
		
		UserAnimeDetail userAnimeDetail = getOrCreateAnimeDetail(animeId, session);
		consumer.accept(userAnimeDetail);
		
		session.save(userAnimeDetail);
	}

	@Override
	public void reset(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		/*
		 *  IDK why, but in MY case i dont need to have CascadeType.ALL in UserAnimeDetail,
		 *  but, if a remove this, or just select all 5 props manually, program won't work ...
		 *  
		 *  So, i have to set this field to null just not to commit FK exception
		 *  (only in case if there's more than 1 user that selected this anime as viewed)
		 */
		
		List<UserAnimeDetail> userAnimeDetailList = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE animeDetail = :theAnimeDetail", 
				UserAnimeDetail.class)
			.setParameter("theAnimeDetail", animeService.getUserAnimeDetail(animeId).getAnimeDetail())
			.getResultList();
		
		if (userAnimeDetailList.size() > 1) {
			session.remove(
						userAnimeDetailList.stream()
							.filter(x -> x.getUser() == userService.find(getAuthUsername()))
							.findFirst()
							.get()
						); 
		} else {
			log.info("Only user " + getAuthUsername() + " had anime with id " + animeId + " as selected -> Removing");
			session.remove(userAnimeDetailList.get(0));
		}
	}

	private UserAnimeDetail getOrCreateAnimeDetail(int animeId, Session session) {
		List<AnimeDetail> animeDetailList = session.createQuery(""
				+ "FROM AnimeDetail "
				+ "WHERE mal_id = :theAnimeId",
				AnimeDetail.class)
			.setParameter("theAnimeId", animeId)
			.getResultList();
		
		AnimeDetail animeDetail = null;
		
		if (animeDetailList.isEmpty()) {
			Anime anime = jikanApiService.findAnime(animeId);
			
			animeDetail = new AnimeDetail(
					animeId, 
					anime.getTitle(), 
					anime.getImages().getJpg().getImage_url()
				);
		} else {
			animeDetail = animeDetailList.get(0);
		}
		
		UserAnimeDetail userAnimeDetail = animeService.getUserAnimeDetail(animeId);
		
		userAnimeDetail.setUser(userService.find(getAuthUsername()));
		userAnimeDetail.setAnimeDetail(animeDetail);
		
		return userAnimeDetail;
	}

	private String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
