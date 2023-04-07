package com.myanimelist.entity.listeners;

import com.myanimelist.entity.UserAnime;

import javax.persistence.PreUpdate;

public class UserAnimeListener {

    @PreUpdate
    public void onPreUpdate(UserAnime userAnime) {
        switch (userAnime.getStatus()) {
            case PLANNING -> {
                userAnime.setFavourite(false);
                userAnime.setScore(0);
            }
            case ON_HOLD, DROPPED -> userAnime.setFavourite(false);
        }
    }
}
