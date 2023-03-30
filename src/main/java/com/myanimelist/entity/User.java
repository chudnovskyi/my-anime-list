package com.myanimelist.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends AuditableEntity<Integer> {

    private String username;
    private String password;
    private String email;
    private String activationCode;

    @Lob
    private byte[] image;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Builder.Default
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    private Set<UserAnime> userAnime = new HashSet<>();
}
