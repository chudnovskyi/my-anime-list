package com.myanimelist.entity;

import com.myanimelist.entity.listeners.UserAnimeListener;
import com.myanimelist.model.AnimeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_anime")
@EntityListeners(UserAnimeListener.class)
public class UserAnime implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int score;
    private boolean favourite;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private AnimeStatus status;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "anime_id")
    private Anime anime;

    public void setUser(User user) {
        this.user = user;
        if (this.user != null) {
            this.user.getUserAnimeList().add(this);
        }
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
        this.anime.getUserAnimeList().add(this);
    }
}
