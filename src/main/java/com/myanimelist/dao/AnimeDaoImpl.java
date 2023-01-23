package com.myanimelist.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.myanimelist.entity.AnimeDetail;
import com.myanimelist.entity.User;
import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.rest.entity.Anime;
import com.myanimelist.service.AnimeService;
import com.myanimelist.service.UserService;

@Repository
public class AnimeDaoImpl implements AnimeDao {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AnimeService animeService;
	
	@Override
	public void setAnimeAsViewed(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		logger.info("@@@ " + currentPrincipalName + " SETTING ANIME WITH ID " + animeId + " AS VIEWD @@@");
		
		User user = userService.findByUsername(currentPrincipalName);
		
		AnimeDetail animeDetail = getAnimeDetail(animeId, session);
		
		if (animeDetail == null) {
			Anime anime = animeService.findAnimeById(animeId);
			
			animeDetail = new AnimeDetail(
					animeId, 
					anime.getTitle(), 
					anime.getImages().getJpg().getImage_url()
				);
		}
		
		if (!isUserAnimeDetailExists(user, animeDetail, session)) {
			UserAnimeDetail userAnimeDetail = new UserAnimeDetail();
			userAnimeDetail.setUser(user);
			userAnimeDetail.setAnimeDetail(animeDetail);
			
			session.save(userAnimeDetail);
		} else {
			logger.info("___________-- ANIME DETAILS ALREADY EXISTS --___________");
		}
	}
	
	@Override
	public List<UserAnimeDetail> getViewedList() {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		List<UserAnimeDetail> userAnimeDetailList = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user_id = :theUser", 
				UserAnimeDetail.class)
			.setParameter("theUser", userService.findByUsername(currentPrincipalName))
			.getResultList();
		
		userAnimeDetailList.sort((o1, o2) -> o2.getScore() - o1.getScore());
		
		return userAnimeDetailList;
	}
	
	private AnimeDetail getAnimeDetail(int animeId, Session session) {
		List<AnimeDetail> animeDetailList = session.createQuery(""
				+ "FROM AnimeDetail "
				+ "WHERE mal_id = :theAnimeId",
				AnimeDetail.class)
			.setParameter("theAnimeId", animeId)
			.getResultList();
		
		return animeDetailList.isEmpty() ? null : animeDetailList.get(0);
	}
	
	private boolean isUserAnimeDetailExists(User theUser, AnimeDetail theAnimeDetail, Session session) {
		List<UserAnimeDetail> animeDetail = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user = :theUser AND "
				+ "animeDetail = :theAnimeDetail", 
				UserAnimeDetail.class)
			.setParameter("theUser", theUser)
			.setParameter("theAnimeDetail", theAnimeDetail)
			.getResultList();
		
		return !animeDetail.isEmpty();
	}
}
