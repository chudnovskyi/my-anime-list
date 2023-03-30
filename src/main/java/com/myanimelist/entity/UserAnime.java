package com.myanimelist.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_anime")
public class UserAnime extends AuditableEntity<Integer> {

    private boolean favourite;
    private boolean watching;
    private boolean planning;
    private boolean completed;
    private boolean onHold;
    private boolean dropped;
    private int score;

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

    public void setAsWatching() {
        setParamsToFalse();
        watching = true;
    }

    public void setAsPlanning() {
        setParamsToFalse();
        setFavourite(false);
        setScore(0);
        planning = true;
    }

    public void setAsCompleted() {
        setParamsToFalse();
        completed = true;
    }

    public void setAsOnHold() {
        setParamsToFalse();
        setFavourite(false);
        onHold = true;
    }

    public void setAsDropped() {
        setParamsToFalse();
        setFavourite(false);
        dropped = true;
    }

    private void setParamsToFalse() {
        setCompleted(false);
        setDropped(false);
        setOnHold(false);
        setWatching(false);
        setPlanning(false);
    }
}
