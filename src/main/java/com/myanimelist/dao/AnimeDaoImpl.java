package com.myanimelist.dao;

import java.util.List;
import java.util.Optional;
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
		
		List<UserAnimeDetail> userAnimeDetails = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user = :theUser AND "
				+ "animeDetail = :theAnimeDetail", 
				UserAnimeDetail.class)
			.setParameter("theUser", user)
			.setParameter("theAnimeDetail", animeDetail)
			.getResultList();

		if (userAnimeDetails.isEmpty()) {
			logger.info("Setting anime as VIEWED");
			UserAnimeDetail userAnimeDetail = new UserAnimeDetail();
			
			userAnimeDetail.setUser(user);
			userAnimeDetail.setAnimeDetail(animeDetail);
			
			session.save(userAnimeDetail);
		} else {
			logger.info("Setting anime as UNviewed");
			
			/*
			 *  IDK why, but in MY case i dont need to have CascadeType.ALL in UserAnimeDetail,
			 *  but, if a remove this, or just select all 5 props manually, program won't work ...
			 *  
			 *  So, i have to set this field to null just not to commit FK exception
			 *  (only in case if there's more than 1 user that selected this anime as viewed)
			 */
			
			int howManyUsersHaveThisAnimeAsViewed = session.createQuery(""
					+ "FROM UserAnimeDetail "
					+ "WHERE animeDetail = :theAnimeDetail", 
					UserAnimeDetail.class)
				.setParameter("theAnimeDetail", animeDetail)
				.getResultList()
				.size();
			
			if (howManyUsersHaveThisAnimeAsViewed > 1) {
				userAnimeDetails.get(0).setAnimeDetail(null);
			}
			
			session.remove(userAnimeDetails.get(0));
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
	
	@Override
	public void setAnimeAsFavourite(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Optional<UserAnimeDetail> userAnimeDetail = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user_id = :theUser", 
				UserAnimeDetail.class)
			.setParameter("theUser", userService.findByUsername(currentPrincipalName))
			.getResultList()
			.stream()
			.filter(animeDetailList -> animeDetailList.getAnimeDetail().getMal_id() == animeId)
			.findFirst();
		
		userAnimeDetail.ifPresentOrElse(
				(x) -> x.setFavourite(!x.isFavourite()), 
				() -> { 
						throw new RuntimeException("setAnimeAsFavourite -> impossible to find animeDetails"); 
					});
	}
	
	@Override
	public void setAnimeScore(int animeId, int score) {
		Session session = entityManager.unwrap(Session.class);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		Optional<UserAnimeDetail> userAnimeDetail = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user_id = :theUser", 
				UserAnimeDetail.class)
			.setParameter("theUser", userService.findByUsername(currentPrincipalName))
			.getResultList()
			.stream()
			.filter(animeDetailList -> animeDetailList.getAnimeDetail().getMal_id() == animeId)
			.findFirst();
		
		userAnimeDetail.ifPresentOrElse(
				(x) -> x.setScore(score), 
				() -> { 
						throw new RuntimeException("setAnimeScore -> impossible to find animeDetails"); 
					});
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
}
