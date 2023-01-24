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
	public UserAnimeDetail getUserAnimeDetail(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		List<UserAnimeDetail> resultList = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE mal_id = :theAnimeId "
				+ "AND user = :theUser",
				UserAnimeDetail.class)
			.setParameter("theAnimeId", animeId)
			.setParameter("theUser", userService.findByUsername(getAuthUsername()))
			.getResultList();
		
		return resultList.isEmpty() ? new UserAnimeDetail() : resultList.get(0);
	}
	
	@Override
	public List<UserAnimeDetail> getUserAnimeDetailList() {
		Session session = entityManager.unwrap(Session.class);
		
		return session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE user_id = :theUser", 
				UserAnimeDetail.class)
			.setParameter("theUser", userService.findByUsername(getAuthUsername()))
			.getResultList()
			.stream()
			.sorted((o1, o2) -> o2.getScore() - o1.getScore())
			.toList();
	}
	
	@Override
	public void setAnimeAsWatching(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		User user = userService.findByUsername(getAuthUsername());
		
		AnimeDetail animeDetail = getOrCreateAnimeDetail(animeId, session);
		UserAnimeDetail userAnimeDetail = getUserAnimeDetail(animeId);
		
		logger.info("Setting anime " + animeId + " as WATCHING");
		
		userAnimeDetail.setUser(user);
		userAnimeDetail.setAnimeDetail(animeDetail);
		userAnimeDetail.setWatching(true);
		
		session.save(userAnimeDetail);
	}

	@Override
	public void setAnimeAsPlanning(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		User user = userService.findByUsername(getAuthUsername());
		
		AnimeDetail animeDetail = getOrCreateAnimeDetail(animeId, session);
		UserAnimeDetail userAnimeDetail = getUserAnimeDetail(animeId);
		
		logger.info("Setting anime " + animeId + " as PLANNING");
		
		userAnimeDetail.setUser(user);
		userAnimeDetail.setAnimeDetail(animeDetail);
		userAnimeDetail.setPlanning(true);
		
		session.save(userAnimeDetail);
	}

	@Override
	public void setAnimeAsCompleted(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		User user = userService.findByUsername(getAuthUsername());
		
		AnimeDetail animeDetail = getOrCreateAnimeDetail(animeId, session);
		UserAnimeDetail userAnimeDetail = getUserAnimeDetail(animeId);
		
		logger.info("Setting anime " + animeId + " as COMPLETED");
		
		userAnimeDetail.setUser(user);
		userAnimeDetail.setAnimeDetail(animeDetail);
		userAnimeDetail.setCompleted(true);
		
		session.save(userAnimeDetail);
	}

	@Override
	public void setAnimeAsHoldOn(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		User user = userService.findByUsername(getAuthUsername());
		
		AnimeDetail animeDetail = getOrCreateAnimeDetail(animeId, session);
		UserAnimeDetail userAnimeDetail = getUserAnimeDetail(animeId);
		
		logger.info("Setting anime " + animeId + " as ON HOLD");
		
		userAnimeDetail.setUser(user);
		userAnimeDetail.setAnimeDetail(animeDetail);
		userAnimeDetail.setOn_hold(true);
		
		session.save(userAnimeDetail);
	}

	@Override
	public void setAnimeAsDropped(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		User user = userService.findByUsername(getAuthUsername());
		
		AnimeDetail animeDetail = getOrCreateAnimeDetail(animeId, session);
		UserAnimeDetail userAnimeDetail = getUserAnimeDetail(animeId);
		
		logger.info("Setting anime " + animeId + " as DROPPED");
		
		userAnimeDetail.setUser(user);
		userAnimeDetail.setAnimeDetail(animeDetail);
		userAnimeDetail.setDropped(true);
		
		session.save(userAnimeDetail);
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
	
	@Override
	public void reset(int animeId) {
		Session session = entityManager.unwrap(Session.class);
		
		logger.info("RESETING ANIME WITH ID " + animeId);
		
		/*
		 *  IDK why, but in MY case i dont need to have CascadeType.ALL in UserAnimeDetail,
		 *  but, if a remove this, or just select all 5 props manually, program won't work ...
		 *  
		 *  So, i have to set this field to null just not to commit FK exception
		 *  (only in case if there's more than 1 user that selected this anime as viewed)
		 */
		
		AnimeDetail animeDetail = getOrCreateAnimeDetail(animeId, session);
		
		List<UserAnimeDetail> userAnimeDetailList = session.createQuery(""
				+ "FROM UserAnimeDetail "
				+ "WHERE animeDetail = :theAnimeDetail", 
				UserAnimeDetail.class)
			.setParameter("theAnimeDetail", animeDetail)
			.getResultList();
		
		if (userAnimeDetailList.size() > 1) {
			session.remove(userAnimeDetailList.stream()
					.filter(x -> x.getUser() == userService.findByUsername(getAuthUsername()))
					.filter(x -> x.getAnimeDetail() == animeDetail)
					.findFirst()
					.get()); 
		} else {
			session.remove(userAnimeDetailList.get(0));
		}
	}
	
	private AnimeDetail getOrCreateAnimeDetail(int animeId, Session session) {
		List<AnimeDetail> animeDetailList = session.createQuery(""
				+ "FROM AnimeDetail "
				+ "WHERE mal_id = :theAnimeId",
				AnimeDetail.class)
			.setParameter("theAnimeId", animeId)
			.getResultList();
		
		if (animeDetailList.isEmpty()) {
			Anime anime = animeService.findAnimeById(animeId);
			
			return new AnimeDetail(
					animeId, 
					anime.getTitle(), 
					anime.getImages().getJpg().getImage_url()
				);
		} else {
			return animeDetailList.get(0);
		}
	}
	
	private String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
