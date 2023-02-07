package com.myanimelist.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.authentication.AuthenticationFacade;
import com.myanimelist.dto.AnimeResponse.Anime;
import com.myanimelist.entity.AnimeDetail;
import com.myanimelist.entity.UserAnimeDetail;
import com.myanimelist.repository.AnimeDetailRepository;
import com.myanimelist.repository.UserAnimeDetailRepository;

@Service
@Transactional
public class AnimeServiceImpl implements AnimeService {

	private UserAnimeDetailRepository userAnimeDetailRepository;
	private AnimeDetailRepository animeDetailRepository;
	
	private JikanApiService jikanApiService;
	private UserService userService;
	
	private AuthenticationFacade authenticationFacade;

	@Autowired
	public AnimeServiceImpl(UserAnimeDetailRepository userAnimeDetailRepository, AnimeDetailRepository animeDetailRepository, 
			JikanApiService jikanApiService, UserService userService, AuthenticationFacade authenticationFacade) {
		this.userAnimeDetailRepository = userAnimeDetailRepository;
		this.animeDetailRepository = animeDetailRepository;
		this.jikanApiService = jikanApiService;
		this.userService = userService;
		this.authenticationFacade = authenticationFacade;
	}
	
	@Override
	public UserAnimeDetail getUserAnimeDetail(int animeId) {
		return userAnimeDetailRepository.findByAnimeDetail_MalIdAndUser_Username(animeId, authenticationFacade.getUsername())
				.orElse(new UserAnimeDetail());
	}

	@Override
	public List<UserAnimeDetail> getUserAnimeDetailList(Predicate<UserAnimeDetail> predicate) {
		return userAnimeDetailRepository.findAllByUser_UsernameOrderByScoreDesc(authenticationFacade.getUsername())
					.stream()
					.filter(predicate)
					.toList();
	}

	@Override
	public void alterUserAnimeDetail(int animeId, Consumer<UserAnimeDetail> consumer) {
		AnimeDetail animeDetail = animeDetailRepository.findById(animeId)
				.orElseGet(() -> {
					Anime anime = jikanApiService.findAnime(animeId);
					return new AnimeDetail(
							animeId, 
							anime.getTitle(), 
							anime.getImages().getJpg().getImageUrl()
						);
				});
			
		UserAnimeDetail userAnimeDetail = getUserAnimeDetail(animeId);
		
		userAnimeDetail.setUser(userService.find(authenticationFacade.getUsername()));
		userAnimeDetail.setAnimeDetail(animeDetail);
		
		consumer.accept(userAnimeDetail);
		
		userAnimeDetailRepository.save(userAnimeDetail);
	}
	
	@Override
	public void reset(int animeId) {
		userAnimeDetailRepository.findAllByAnimeDetail_MalId(animeId)
				.stream()
				.filter(x -> x.getUser().getUsername().equals(authenticationFacade.getUsername()))
				.findFirst()
				.ifPresent(userAnimeDetailRepository::delete);
	}
}
